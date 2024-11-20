package com.apiRestProject.acm.persistence.repository;


import com.apiRestProject.acm.persistence.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query(value = "SELECT l FROM Libro l WHERE l.titulo = :titulo")
    Optional<Libro> findByTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.categoria.nombreCategoria = :categoria")
    List<Libro> findByCategoria(@Param("categoria") String categoria);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :autor")
    List<Libro> findByAutor(@Param("autor") String autor);

    @Query("SELECT l.libros FROM Prestamo l JOIN l.cliente c on c.idCliente = :idCliente WHERE l.idPrestamo = :idPrestamo")
    List<Libro> findByPrestamoUsuario(@Param("idCliente") Long idCliente, @Param("idPrestamo") Long idPrestamo);

    @Modifying
    @Transactional
    @Query("DELETE FROM Libro l WHERE l.titulo = :titulo")
    void deleteByTitulo(@Param("titulo") String titulo);

}
