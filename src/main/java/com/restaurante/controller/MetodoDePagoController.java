package com.restaurante.controller;

import com.restaurante.model.MetodoDePago;
import com.restaurante.service.MetodoDePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/metodos-pago")
public class MetodoDePagoController {

    @Autowired
    private MetodoDePagoService metodoDePagoService;

    @GetMapping
    public List<MetodoDePago> listar() {
        return metodoDePagoService.listar();
    }

    @PostMapping
    public MetodoDePago guardar(@RequestBody MetodoDePago metodoDePago) {
        return metodoDePagoService.guardar(metodoDePago);
    }

    @PutMapping("/{id}")
    public MetodoDePago actualizar(@PathVariable Long id, @RequestBody MetodoDePago metodoDePago) {
        return metodoDePagoService.actualizar(id, metodoDePago);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        metodoDePagoService.eliminar(id);
    }
}
