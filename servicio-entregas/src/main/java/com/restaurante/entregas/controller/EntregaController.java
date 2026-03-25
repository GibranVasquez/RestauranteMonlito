package com.restaurante.entregas.controller;

import com.restaurante.entregas.model.Entrega;
import com.restaurante.entregas.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @GetMapping
    public List<Entrega> listarEntregas() {
        return entregaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrega> obtenerEntrega(@PathVariable Long id) {
        Optional<Entrega> entrega = entregaService.findById(id);
        return entrega.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Entrega crearEntrega(@RequestBody Entrega entrega) {
        return entregaService.save(entrega);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Entrega> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        Entrega entregaActualizada = entregaService.actualizarEstado(id, estado);
        if (entregaActualizada != null) {
            return ResponseEntity.ok(entregaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEntrega(@PathVariable Long id) {
        entregaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
