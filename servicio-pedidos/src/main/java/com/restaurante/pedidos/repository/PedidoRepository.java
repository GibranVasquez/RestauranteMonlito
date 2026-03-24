package com.restaurante.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.pedidos.model.Pedido;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
}
