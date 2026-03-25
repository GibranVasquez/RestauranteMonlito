package com.restaurante.clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.clientes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long>{
}
