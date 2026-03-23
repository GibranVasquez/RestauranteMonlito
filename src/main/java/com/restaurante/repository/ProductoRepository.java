package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto,Long>{
	List<Producto> findByCategoriaId(Long categoriaId);

	Optional<Producto> findByIdAndCategoriaId(Long id, Long categoriaId);
}