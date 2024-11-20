package com.apiRestProject.acm.persistence.repository;

import com.apiRestProject.acm.persistence.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    @Query(value = "SELECT v FROM Prestamo v WHERE v.cliente.idCliente = :id")
    Optional<Prestamo> findByClienteId(@Param("id") Long id);

    /*@Query(value = "SELECT v FROM Prestamo v WHERE v.idPrestamo = :id")
    Optional<Prestamo> findById(@Param("id") Long id);*/

}
