package com.restaurante.service;

import com.restaurante.model.Pedido;
import com.restaurante.model.Producto;
import com.restaurante.model.Reporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private PedidoService pedidoService;

    public Reporte obtenerReporteDiario() {
        LocalDate hoy = LocalDate.now();
        return generar("DIARIO", hoy, hoy, pedidoService.listar());
    }

    public Reporte obtenerReporteSemanal() {
        LocalDate fin = LocalDate.now();
        LocalDate inicio = fin.minusDays(6);
        return generar("SEMANAL", inicio, fin, pedidoService.listar());
    }

    private Reporte generar(String tipo, LocalDate inicio, LocalDate fin, List<Pedido> pedidos) {
        List<Pedido> filtrados = pedidos.stream()
                .filter(p -> p.getFecha() != null)
                .filter(p -> {
                    LocalDate fechaPedido = p.getFecha().toLocalDate();
                    return !fechaPedido.isBefore(inicio) && !fechaPedido.isAfter(fin);
                })
                .toList();

        int totalPedidos = filtrados.size();

        int totalProductosVendidos = filtrados.stream()
                .map(Pedido::getProductos)
                .map(lista -> lista == null ? Collections.<Producto>emptyList() : lista)
                .mapToInt(List::size)
                .sum();

        double montoTotal = filtrados.stream()
                .map(Pedido::getProductos)
                .map(lista -> lista == null ? Collections.<Producto>emptyList() : lista)
                .flatMap(List::stream)
                .mapToDouble(Producto::getPrecio)
                .sum();

        return new Reporte(tipo, inicio, fin, totalPedidos, totalProductosVendidos, montoTotal);
    }
}
