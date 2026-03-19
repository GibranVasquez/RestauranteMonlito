package com.restaurante.controller;

import com.restaurante.model.Categoria;
import com.restaurante.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/categorias")

public class CategoriaController {

@Autowired
private CategoriaRepository repo;

@GetMapping
public List<Categoria> listar(){
return repo.findAll();
}

@PostMapping
public Categoria guardar(@RequestBody Categoria c){
return repo.save(c);
}

@PutMapping("/{id}")
public Categoria actualizar(@PathVariable Long id,@RequestBody Categoria c){
c.setId(id);
return repo.save(c);
}

@DeleteMapping("/{id}")
public void eliminar(@PathVariable Long id){
repo.deleteById(id);
}

}