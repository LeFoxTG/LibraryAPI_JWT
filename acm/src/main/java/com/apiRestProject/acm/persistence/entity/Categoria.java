package com.apiRestProject.acm.persistence.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@ToString(of = {"idCategoria","nombreCategoria"})
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idCategoria", nullable = false, unique = true)
    private Long idCategoria;
    @Column(name = "nombreCategoria")
    private String nombreCategoria;
    @Column(name = "descripcion")
    private String descripcion;

    public Categoria(Long idCategoria, String nombreCategoria, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private Set<Libro> libros = new HashSet<>();
}
