package com.restaurante.controller;

import com.restaurante.model.Pedido;
import com.restaurante.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/pedidos")

public class PedidoController {

@Autowired
private PedidoService pedidoService;

@GetMapping
public List<Pedido> listar(){
return pedidoService.listar();
}

@PostMapping
public Pedido guardar(@RequestBody Pedido p){
return pedidoService.guardar(p);
}

@PutMapping("/{id}")
public Pedido actualizar(@PathVariable Long id,@RequestBody Pedido p){
p.setId(id);
return pedidoService.guardar(p);
}

@DeleteMapping("/{id}")
public void eliminar(@PathVariable Long id){
pedidoService.eliminar(id);
}

}