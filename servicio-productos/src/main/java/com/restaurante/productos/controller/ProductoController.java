package com.restaurante.productos.controller;

import com.restaurante.productos.model.Producto;
import com.restaurante.productos.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/productos")

public class ProductoController {

@Autowired
private ProductoRepository repo;

@GetMapping
public List<Producto> listar(){
return repo.findAll();
}

@GetMapping("/{id}")
public Producto obtener(@PathVariable Long id){
return repo.findById(id).orElse(null);
}

@PostMapping
public Producto guardar(@RequestBody Producto p){
return repo.save(p);
}

@PutMapping("/{id}")
public Producto actualizar(@PathVariable Long id,@RequestBody Producto p){
p.setId(id);
return repo.save(p);
}

@DeleteMapping("/{id}")
public void eliminar(@PathVariable Long id){
repo.deleteById(id);
}

}
