package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	List<Pedido> findByClienteId(Long clienteId);

	Optional<Pedido> findByIdAndClienteId(Long id, Long clienteId);
}