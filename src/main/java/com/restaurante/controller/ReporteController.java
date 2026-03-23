package com.restaurante.controller;

import com.restaurante.model.Reporte;
import com.restaurante.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/diario")
    public Reporte diario() {
        return reporteService.obtenerReporteDiario();
    }

    @GetMapping("/semanal")
    public Reporte semanal() {
        return reporteService.obtenerReporteSemanal();
    }
}
