package com.restaurante.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante.repository.CategoriaRepository;
import com.restaurante.model.Categoria;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> listar(){
        return repo.findAll();
    }

    public Optional<Categoria> obtenerPorId(Long id) {
        return repo.findById(id);
    }

    public Categoria guardar(Categoria c){
        return repo.save(c);
    }
}