package com.restaurante.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.productos.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Long>{
}
