package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>{
}