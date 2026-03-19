package com.restaurante.service;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante.repository.ProductoRepository;
import com.restaurante.model.Producto;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    public List<Producto> listar(){
        return repo.findAll();
    }

    public Producto guardar(Producto p){
        return repo.save(p);
    }
}