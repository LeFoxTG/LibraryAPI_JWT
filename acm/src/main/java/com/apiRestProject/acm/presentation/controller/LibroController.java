package com.apiRestProject.acm.presentation.controller;

import com.apiRestProject.acm.presentation.dto.CreateLibroRequestDTO;
import com.apiRestProject.acm.presentation.dto.LibroResponseDTO;
import com.apiRestProject.acm.presentation.dto.UpdateLibroDTO;
import com.apiRestProject.acm.services.LibroService;
import com.apiRestProject.acm.utils.OpenLibrabyClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping(value = "/acmLib")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;
    private OpenLibrabyClient openLibabyClient;

    @GetMapping("/all")
    public ResponseEntity<?> getAllLibros() {
        List<LibroResponseDTO> response = libroService.getLibros();
        if (Objects.isNull(response) ||response.isEmpty()){
            return ResponseEntity.badRequest().body("No hay libros disponibles.");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createLibro(@Valid @RequestBody CreateLibroRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }
        LibroResponseDTO response = libroService.createLibro(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/byCategoria",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByCategoria(@RequestBody String categoria){
        List<LibroResponseDTO> response = libroService.getLibroByCategoria(categoria);
        if (response == null || response.isEmpty()) {
            return ResponseEntity.badRequest().body("No se encontraron libros para la categoría: " + categoria);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/byAutor",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByAutor(@RequestBody String autor){
        List<LibroResponseDTO> response = libroService.getLibroByAutor(autor);
        if (response == null || response.isEmpty()) {
            return ResponseEntity.badRequest().body("No se encontraron libros para el autor: " + autor);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/byPrestamo")
    public ResponseEntity<?> getByPrestamo(@RequestParam Long idCliente, @RequestParam Long idPrestamo, Authentication authentication){
        List<LibroResponseDTO> response = libroService.getLibroByPrestamo(idCliente, idPrestamo, authentication);
        if (response == null || response.isEmpty()) {
            return ResponseEntity.badRequest().body("No se encontraron libros para el prestamo");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateLibro(@Valid @RequestBody UpdateLibroDTO request, @RequestParam String title, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            LibroResponseDTO response = libroService.updateLibro(request, title);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteLibro(@RequestParam String title) {
        try {
            libroService.deleteByTitulo(title);
            return ResponseEntity.ok("Libro eliminado exitosamente.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
