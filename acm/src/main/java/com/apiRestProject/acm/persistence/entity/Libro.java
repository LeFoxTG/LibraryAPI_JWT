package com.apiRestProject.acm.persistence.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Libro {
    @Id
    @Column(name = "idLibro", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "anioPublicacion")
    private LocalDate anioPublicacion;
    @Column(name = "disponibilidad")
    private Boolean disponibilidad;
    @Column(name = "descripcion")
    private String descripcion;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(
            name = "prestamo_libro",
            joinColumns = {@JoinColumn(name = "idLibro")},
            inverseJoinColumns = {@JoinColumn(name = "idPrestamo")}
    )
    private Set<Prestamo> prestamos = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "idCategoriaFK")
    private Categoria categoria;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "idAutorFK")
    private Autor autor;

    public Libro(String titulo, LocalDate anioPublicacion, Boolean disponibilidad, String descripcion/*, Set<PrestamoLibro> prestamos*/, Categoria categoria) {
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.disponibilidad = disponibilidad;
        this.descripcion = descripcion;
        //this.prestamos = prestamos;
        this.categoria = categoria;
    }

    public void addPrestamoLibro(Prestamo prestamo) {
        this.prestamos.add(prestamo);
    }

}
