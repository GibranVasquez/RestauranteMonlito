package com.restaurante.service;

import com.restaurante.model.Cliente;
import com.restaurante.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminarCliente(Long id) {
        clienteRepository.findById(id).ifPresent(cliente -> {
            cliente.getPedidos().clear();
            clienteRepository.save(cliente); // Guarda para activar orphanRemoval
            clienteRepository.delete(cliente); // Ahora elimina el cliente
        });
    }
}