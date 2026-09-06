package com.tienda.catalogo.repository;

import com.tienda.catalogo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByDestacadoTrue();

    List<Producto> findByCategoriaId(Long categoriaId);
}
