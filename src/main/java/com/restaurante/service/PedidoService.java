package com.restaurante.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante.repository.PedidoRepository;
import com.restaurante.model.Pedido;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public List<Pedido> listar(){
        return repo.findAll();
    }

    public List<Pedido> listarPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public Optional<Pedido> obtenerPorCliente(Long clienteId, Long pedidoId) {
        return repo.findByIdAndClienteId(pedidoId, clienteId);
    }

    public Pedido guardar(Pedido p){
        return repo.save(p);
    }

    @Transactional
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}