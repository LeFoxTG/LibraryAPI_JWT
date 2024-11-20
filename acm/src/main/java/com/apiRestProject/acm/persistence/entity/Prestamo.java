package com.apiRestProject.acm.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Entity
@NoArgsConstructor
@ToString(of = {
        "idPrestamo",
        "fechaInicioPrestamo",
        "fechaFinPrestamo"
})
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idPrestamo",nullable = false,unique = true)
    private Long idPrestamo;
    @Setter
    private LocalDate fechaInicioPrestamo;
    private LocalDate fechaFinPrestamo;

    @ManyToOne
    @JoinColumn(name = "idClienteFK", referencedColumnName = "idCliente")
    @Setter
    private Cliente cliente;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "prestamo_libro",
            joinColumns = @JoinColumn(name = "idPrestamo"),
            inverseJoinColumns = @JoinColumn(name = "idLibro")
    )
    private Set<Libro> libros = new HashSet<>();


    public void addPrestamoLibro(Libro prestamo) {
        libros.add(prestamo);
    }

    public Prestamo(Long id, LocalDate fechaInicioPrestamo, LocalDate fechaFinPrestamo) {
        this.idPrestamo = id;
        this.fechaInicioPrestamo = fechaInicioPrestamo;
        this.fechaFinPrestamo = fechaFinPrestamo;
    }
    public Prestamo(Long id, LocalDate fechaInicioPrestamo, LocalDate fechaFinPrestamo, Cliente cliente) {
        this.idPrestamo = id;
        this.fechaInicioPrestamo = fechaInicioPrestamo;
        this.fechaFinPrestamo = fechaFinPrestamo;
        this.cliente = cliente;
    }
}
