package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.model.MetodoDePago;

public interface MetodoDePagoRepository extends JpaRepository<MetodoDePago,Long>{
}