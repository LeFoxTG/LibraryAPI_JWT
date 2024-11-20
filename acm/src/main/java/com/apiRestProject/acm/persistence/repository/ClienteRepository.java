package com.apiRestProject.acm.persistence.repository;


import com.apiRestProject.acm.persistence.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    @Query(value = "SELECT c FROM Cliente c WHERE c.nombre=:#{#nombre}")
    Optional<Cliente> findByNombre(@Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Cliente WHERE nombre= :nombre")
    Integer deleteByNombre(String nombre);
}
