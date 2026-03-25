package com.restaurante.productos.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.restaurante.productos.repository.ProductoRepository;
import com.restaurante.productos.model.Producto;

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

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    public Producto obtenerPorId(Long id){
        return repo.findById(id).orElse(null);
    }
}
