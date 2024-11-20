package com.apiRestProject.acm.services.impl;

import com.apiRestProject.acm.persistence.entity.*;
import com.apiRestProject.acm.persistence.repository.*;
import com.apiRestProject.acm.presentation.dto.CreateLibroRequestDTO;
import com.apiRestProject.acm.presentation.dto.LibroResponseDTO;
import com.apiRestProject.acm.presentation.dto.UpdateLibroDTO;
import com.apiRestProject.acm.services.LibroService;
import com.apiRestProject.acm.utils.OpenLibrabyClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibroServiceImpl implements LibroService {

    private final OpenLibrabyClient openLibrabyClient;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final ClienteRepository clienteRepository;
    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<LibroResponseDTO> getLibros(){
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            return List.of();
        }

        return libros.stream()
                .map(libro -> new LibroResponseDTO(
                        libro.getTitulo(),
                        libro.getAnioPublicacion(),
                        libro.getDisponibilidad(),
                        libro.getDescripcion(),
                        libro.getAutor().getNombre(),
                        libro.getCategoria().getNombreCategoria()
                ))
                .collect(Collectors.toList());

    }

    @Override
    public LibroResponseDTO getLibroByTitle(String title){
        Optional<Libro> libro = libroRepository.findByTitulo(title);

        Libro result = libro.get();
        return new LibroResponseDTO(
                result.getTitulo(),
                result.getAnioPublicacion(),
                result.getDisponibilidad(),
                result.getDescripcion(),
                result.getAutor().getNombre(),
                result.getCategoria().getNombreCategoria()
        );
    }

    @Override
    public List<LibroResponseDTO> getLibroByCategoria(String categoria){
        List<Libro> libro = libroRepository.findByCategoria(categoria);
        if(!libro.isEmpty()) {
            return libro.stream()
                    .map(libros -> new LibroResponseDTO(
                            libros.getTitulo(),
                            libros.getAnioPublicacion(),
                            libros.getDisponibilidad(),
                            libros.getDescripcion(),
                            libros.getAutor().getNombre(),
                            libros.getCategoria().getNombreCategoria()
                    ))
                    .collect(Collectors.toList());
        }else{
            List<LibroResponseDTO> open = openLibrabyClient.searchLibroByCategoria(categoria);
            if(!open.isEmpty()) {
                return open;
            }else {
                throw new NoSuchElementException("No se encontró libro con la categoría: " + categoria);
            }
        }
    }

    @Override
    public List<LibroResponseDTO> getLibroByAutor(String autor){
        List<Libro> libro = libroRepository.findByAutor(autor);
        if(!libro.isEmpty()) {
            return libro.stream()
                    .map(libros -> new LibroResponseDTO(
                            libros.getTitulo(),
                            libros.getAnioPublicacion(),
                            libros.getDisponibilidad(),
                            libros.getDescripcion(),
                            libros.getAutor().getNombre(),
                            libros.getCategoria().getNombreCategoria()
                    ))
                    .collect(Collectors.toList());
        }else{
            List<LibroResponseDTO> open = openLibrabyClient.searchLibroByAutor(autor);
            if(!open.isEmpty()) {
                return open;
            }else {
                throw new NoSuchElementException("No se encontró libro con el autor: " + autor);
            }
        }
    }

    @Override
    public List<LibroResponseDTO> getLibroByPrestamo(Long idCliente, Long idPrestamo, Authentication authentication){
        String username = authentication.getName();
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if(Objects.equals(usuario.get().getRol(), "ADMIN") || Objects.equals(usuario.get().getCliente().getIdCliente(), idCliente)) {
            List<Libro> libro = libroRepository.findByPrestamoUsuario(idCliente, idPrestamo);
            if(!libro.isEmpty()) {
                return libro.stream()
                        .map(libros -> new LibroResponseDTO(
                                libros.getTitulo(),
                                libros.getAnioPublicacion(),
                                libros.getDisponibilidad(),
                                libros.getDescripcion(),
                                libros.getAutor().getNombre(),
                                libros.getCategoria().getNombreCategoria()
                        ))
                        .collect(Collectors.toList());
            }else{
                throw new NoSuchElementException("No se encontró libro con el prestamo: " + idPrestamo + "ni el cliente: " + idCliente);
            }
        }else{
            throw new NoSuchElementException("No tienes permiso para acceder a este préstamo.");
        }
    }

    @Override
    public LibroResponseDTO createLibro(CreateLibroRequestDTO rlibro){
        Optional<Autor> autor = autorRepository.findById(rlibro.getIdAutor());
        Optional<Categoria> categoria = categoriaRepository.findById(rlibro.getIdCategoria());
        Libro libro = Libro.builder()
                .titulo(rlibro.getTitulo())
                .anioPublicacion(rlibro.getAnioPublicacion())
                .disponibilidad(rlibro.getDisponible())
                .descripcion(rlibro.getDescripcion())
                .categoria(categoria.get())
                .autor(autor.get())
                .build();
        var result = libroRepository.save(libro);
        return new LibroResponseDTO(
                result.getTitulo(),
                result.getAnioPublicacion(),
                result.getDisponibilidad(),
                result.getDescripcion(),
                result.getAutor().getNombre(),
                result.getCategoria().getNombreCategoria()
        );
    }

    @Override
    public LibroResponseDTO updateLibro(UpdateLibroDTO ulibro, String title){
        Optional<Libro> libro = libroRepository.findByTitulo(title);
        Libro result = libro.get();
        result.setTitulo(ulibro.getTitulo());
        result.setAnioPublicacion(ulibro.getAnioPublicacion());
        result.setDescripcion(ulibro.getDescripcion());
        result.setDisponibilidad(ulibro.getDisponible());
        Libro updatedLibro = libroRepository.save(result);
        return new LibroResponseDTO(
                updatedLibro.getTitulo(),
                updatedLibro.getAnioPublicacion(),
                updatedLibro.getDisponibilidad(),
                updatedLibro.getDescripcion(),
                updatedLibro.getAutor().getNombre(),
                updatedLibro.getCategoria().getNombreCategoria()
        );
    }

    @Override
    public void deleteByTitulo(String title) {
        Optional<Libro> libro = libroRepository.findByTitulo(title);

        if (libro.isEmpty()) {
            throw new NoSuchElementException("Libro con título '" + title + "' no encontrado.");
        }

        libroRepository.delete(libro.get());
    }
}
