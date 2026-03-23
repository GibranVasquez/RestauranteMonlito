package com.restaurante.controller;

import com.restaurante.model.Categoria;
import com.restaurante.model.Producto;
import com.restaurante.service.CategoriaService;
import com.restaurante.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categorias/{categoriaId}/productos")
public class CategoriaProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Producto> listarPorCategoria(@PathVariable Long categoriaId) {
        validarCategoria(categoriaId);
        return productoService.listarPorCategoria(categoriaId);
    }

    @GetMapping("/{productoId}")
    public Producto obtenerPorCategoria(@PathVariable Long categoriaId, @PathVariable Long productoId) {
        validarCategoria(categoriaId);
        return productoService.obtenerPorCategoria(categoriaId, productoId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Producto no encontrado para la categoria"));
    }

    @PostMapping
    public Producto crearParaCategoria(@PathVariable Long categoriaId, @RequestBody Producto producto) {
        Categoria categoria = validarCategoria(categoriaId);
        producto.setCategoria(categoria);
        return productoService.guardar(producto);
    }

    private Categoria validarCategoria(Long categoriaId) {
        return categoriaService.obtenerPorId(categoriaId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Categoria no encontrada"));
    }
}
