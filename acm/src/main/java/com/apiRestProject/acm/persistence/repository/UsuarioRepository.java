package com.apiRestProject.acm.persistence.repository;

import com.apiRestProject.acm.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    /*@Query("SELECT u FROM Usuario u WHERE u.username = :user")
    Optional<Usuario> findByUsuario(@Param("user") String user);*/

    Optional<Usuario> findByUsername(String username);
}
