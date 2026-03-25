package com.restaurante.pedidos.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante.pedidos.repository.PedidoRepository;
import com.restaurante.pedidos.model.Pedido;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public List<Pedido> obtenerPedidos() {
        return repo.findAll();
    }

    public Pedido guardarPedido(Pedido p) {
        return repo.save(p);
    }

    public void eliminarPedido(Long id) {
        repo.deleteById(id);
    }

    public Pedido obtenerPedidoPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Pedido> obtenerPedidosPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }
}
