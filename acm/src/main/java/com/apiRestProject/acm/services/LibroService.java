package com.apiRestProject.acm.services;

import com.apiRestProject.acm.presentation.dto.CreateLibroRequestDTO;
import com.apiRestProject.acm.presentation.dto.LibroResponseDTO;
import com.apiRestProject.acm.presentation.dto.UpdateLibroDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LibroService {

    public List<LibroResponseDTO> getLibros();
    public LibroResponseDTO getLibroByTitle(String title);
    public List<LibroResponseDTO> getLibroByCategoria(String categoria);
    public List<LibroResponseDTO> getLibroByAutor(String autor);
    public List<LibroResponseDTO> getLibroByPrestamo(Long idCliente, Long idPrestamo, Authentication authentication);
    public LibroResponseDTO createLibro(CreateLibroRequestDTO libro);
    public LibroResponseDTO updateLibro(UpdateLibroDTO libro, String title);
    public void deleteByTitulo(String titulo);

}
