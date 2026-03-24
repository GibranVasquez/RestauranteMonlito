package com.restaurante.clientes.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.restaurante.clientes.repository.ClienteRepository;
import com.restaurante.clientes.model.Cliente;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> obtenerClientes(){
        return repo.findAll();
    }

    public Cliente guardarCliente(Cliente c){
        return repo.save(c);
    }

    public void eliminarCliente(Long id){
        repo.deleteById(id);
    }

    public Cliente obtenerClientePorId(Long id){
        return repo.findById(id).orElse(null);
    }
}
