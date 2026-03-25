package com.restaurante.pagos.controller;

import com.restaurante.pagos.model.MetodoDePago;
import com.restaurante.pagos.service.MetodoDePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
