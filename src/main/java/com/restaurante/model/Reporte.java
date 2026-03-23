package com.restaurante.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int totalPedidos;
    private int totalProductosVendidos;
    private double montoTotal;

    public Reporte() {
    }

    public Reporte(String tipo, LocalDate fechaInicio, LocalDate fechaFin, int totalPedidos,
                   int totalProductosVendidos, double montoTotal) {
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.totalPedidos = totalPedidos;
        this.totalProductosVendidos = totalProductosVendidos;
        this.montoTotal = montoTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(int totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public int getTotalProductosVendidos() {
        return totalProductosVendidos;
    }

    public void setTotalProductosVendidos(int totalProductosVendidos) {
        this.totalProductosVendidos = totalProductosVendidos;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }
}
