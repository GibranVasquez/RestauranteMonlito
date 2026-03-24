package com.restaurante.productos.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.restaurante.productos.repository.CategoriaRepository;
import com.restaurante.productos.model.Categoria;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> listar(){
        return repo.findAll();
    }

    public Categoria guardar(Categoria c){
        return repo.save(c);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    public Categoria obtenerPorId(Long id){
        return repo.findById(id).orElse(null);
    }
}
