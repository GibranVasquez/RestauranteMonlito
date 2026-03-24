package com.restaurante.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.productos.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>{
}
