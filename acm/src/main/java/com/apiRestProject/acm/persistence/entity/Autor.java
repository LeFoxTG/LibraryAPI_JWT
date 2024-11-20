package com.apiRestProject.acm.persistence.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString(of = {"idAutor","nombre","paisOrigen"})
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idAutor", nullable = false, unique = true)
    private Long idAutor;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "paisOrigen")
    private String paisOrigen;

    @JsonIgnore
    @OneToMany(mappedBy = "autor")
    private Set<Libro> libros = new HashSet<>();

}
