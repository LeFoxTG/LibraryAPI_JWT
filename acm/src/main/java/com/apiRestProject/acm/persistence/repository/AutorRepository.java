package com.apiRestProject.acm.persistence.repository;

import com.apiRestProject.acm.persistence.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query(value = "SELECT a FROM Autor a WHERE a.nombre =: nombre")
    Optional<Autor> findByNombre(@Param("nombre")String nombre);
}
