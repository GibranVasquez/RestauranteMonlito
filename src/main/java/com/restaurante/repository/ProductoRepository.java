package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Long>{
}