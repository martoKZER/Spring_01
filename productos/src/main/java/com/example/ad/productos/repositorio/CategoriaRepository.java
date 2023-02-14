package com.example.ad.productos.repositorio;

import com.example.ad.productos.model.Categoria;
import com.example.ad.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
