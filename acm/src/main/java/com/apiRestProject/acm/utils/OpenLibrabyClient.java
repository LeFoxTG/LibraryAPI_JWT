package com.apiRestProject.acm.utils;

import com.apiRestProject.acm.presentation.dto.LibroResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class OpenLibrabyClient {

    private final String baseOpenLibrary = "https://openlibrary.org";

    private final RestTemplate restTemplate;

    public OpenLibrabyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LibroResponseDTO searchLibroByTitulo(String title) {
        String apiUrl = String.format("%s/search.json?title=%s", baseOpenLibrary, title);
        return fetchFirstBook(apiUrl, "Libro no encontrado en OpenLibrary con título: " + title);
    }

    public List<LibroResponseDTO> searchLibroByAutor(String author) {
        String apiUrl = String.format("%s/search.json?author=%s", baseOpenLibrary, author);
        return fetchBooks(apiUrl, "No se encontraron libros para el autor: " + author);
    }

    public List<LibroResponseDTO> searchLibroByCategoria(String category) {
        String apiUrl = String.format("%s/search.json?subject=%s", baseOpenLibrary, category);
        return fetchBooks(apiUrl, "No se encontraron libros para la categoría: " + category);
    }

    private LibroResponseDTO fetchFirstBook(String apiUrl, String errorMessage) {
        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(apiUrl, JsonNode.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode docs = response.getBody().get("docs");
                if (docs != null && docs.isArray() && !docs.isEmpty()) {
                    JsonNode firstResult = docs.get(0);

                    // Verifica si "first_publish_year" es un valor válido y lo convierte a Integer
                    Integer year = null;
                    if (firstResult.has("first_publish_year")) {
                        try {
                            year = Integer.parseInt(firstResult.get("first_publish_year").asText());
                        } catch (NumberFormatException e) {
                            // Si no se puede parsear el año, lo dejamos como null
                            year = null;
                        }
                    }

                    return new LibroResponseDTO(
                            firstResult.get("title").asText(),
                            year != null ? LocalDate.of(year, 1, 1) : null,  // Si hay un año, se usa como fecha con el 1 de enero
                            true,
                            firstResult.has("description") ? firstResult.get("description").asText() : "Descripción no disponible",
                            firstResult.has("author_name") ? firstResult.get("author_name").get(0).asText() : "Autor desconocido",
                            firstResult.has("subject") ? firstResult.get("subject").get(0).asText() : "Categoría desconocida"
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error consultando OpenLibrary: " + e.getMessage());
        }

        throw new EntityNotFoundException(errorMessage);
    }

    private List<LibroResponseDTO> fetchBooks(String apiUrl, String errorMessage) {
        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(apiUrl, JsonNode.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode docs = response.getBody().get("docs");
                if (docs != null && docs.isArray() && !docs.isEmpty()) {
                    return StreamSupport.stream(docs.spliterator(), false)
                            .map(doc -> {
                                // Verifica si "first_publish_year" es un valor válido y lo convierte a Integer
                                Integer year = null;
                                if (doc.has("first_publish_year")) {
                                    try {
                                        year = Integer.parseInt(doc.get("first_publish_year").asText());
                                    } catch (NumberFormatException e) {
                                        // Si no se puede parsear el año, lo dejamos como null
                                        year = null;
                                    }
                                }

                                return new LibroResponseDTO(
                                        doc.get("title").asText(),
                                        year != null ? LocalDate.of(year, 1, 1) : null,  // Si hay un año, se usa como fecha con el 1 de enero
                                        true,
                                        doc.has("description") ? doc.get("description").asText() : "Descripción no disponible",
                                        doc.has("author_name") ? doc.get("author_name").get(0).asText() : "Autor desconocido",
                                        doc.has("subject") ? doc.get("subject").get(0).asText() : "Categoría desconocida"
                                );
                            })
                            .toList();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error consultando OpenLibrary: " + e.getMessage());
        }

        throw new EntityNotFoundException(errorMessage);
    }
}
