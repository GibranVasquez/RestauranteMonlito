package com.restaurante.controller;

import com.restaurante.model.Producto;
import com.restaurante.repository.ProductoRepository;

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