package com.restaurante.pagos.repository;

import com.restaurante.pagos.model.MetodoDePago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetodoDePagoRepository extends JpaRepository<MetodoDePago, Long> {
}
