package com.apiRestProject.acm.presentation.dto;


import java.time.LocalDate;

public record LibroResponseDTO(
        String titulo,
        LocalDate anioPublicacion,
        Boolean disponibilidad,
        String descripcion,
        String autor,
        String categoria
        )
{
}
