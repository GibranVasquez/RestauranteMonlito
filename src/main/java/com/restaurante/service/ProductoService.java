package com.restaurante.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
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

    public List<Producto> listarPorCategoria(Long categoriaId) {
        return repo.findByCategoriaId(categoriaId);
    }

    public Optional<Producto> obtenerPorCategoria(Long categoriaId, Long productoId) {
        return repo.findByIdAndCategoriaId(productoId, categoriaId);
    }

    public Producto guardar(Producto p){
        return repo.save(p);
    }
}