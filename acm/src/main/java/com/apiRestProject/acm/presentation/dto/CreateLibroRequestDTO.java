package com.apiRestProject.acm.presentation.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateLibroRequestDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String titulo;
    @NotNull
    private LocalDate anioPublicacion;
    @NotNull
    private Boolean disponible;
    @NotBlank
    @Size(min = 10, max = 255)
    private String descripcion;
    @Positive
    private Long idAutor;
    @Positive
    private Long idCategoria;
}
