package com.apiRestProject.acm.persistence.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString(of = {
        "idCliente",
        "nombre",
        "correo",
        "telefono",
        "estadoCuenta"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "idCliente", nullable = false,unique = true)
    private Long idCliente;
    @Setter
    private String nombre;
    @Setter
    private String correo;
    @Setter
    private Long telefono;
    @Setter
    private boolean estadoCuenta;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Prestamo> prestamos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "idUsuarioFK", referencedColumnName = "idUsuario")
    private Usuario usuario;

    public Cliente() {

    }

    public void addPrestamo(Prestamo prestamo) {
        this.prestamos.add(prestamo);
        prestamo.setCliente(this);
    }
}
