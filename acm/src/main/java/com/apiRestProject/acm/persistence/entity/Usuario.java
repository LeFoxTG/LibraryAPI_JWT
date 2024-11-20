package com.apiRestProject.acm.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "usuario")
public class Usuario
{
    @Id
    @Column(name = "idUsuario", nullable = false, unique = true)
    private Long idUsuario;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "rol")
    private String rol;

    @OneToOne(mappedBy = "usuario")
    private Cliente cliente;
}
