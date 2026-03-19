package com.restaurante.controller;

import com.restaurante.model.Cliente;
import com.restaurante.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/clientes")

public class ClienteController {

@Autowired
private ClienteService clienteService;

@GetMapping
public List<Cliente> listar(){
return clienteService.obtenerClientes();
}

@PostMapping
public Cliente guardar(@RequestBody Cliente c){
return clienteService.guardarCliente(c);
}

@PutMapping("/{id}")
public Cliente actualizar(@PathVariable Long id,@RequestBody Cliente c){
c.setId(id);
return clienteService.guardarCliente(c);
}

@DeleteMapping("/{id}")
public void eliminar(@PathVariable Long id){
clienteService.eliminarCliente(id);
}

}