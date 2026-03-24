package com.restaurante.pedidos.controller;

import com.restaurante.pedidos.model.Pedido;
import com.restaurante.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.obtenerPedidos();
    }

    @GetMapping("/{id}")
    public Pedido obtener(@PathVariable Long id) {
        return pedidoService.obtenerPedidoPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> listarPorCliente(@PathVariable Long clienteId) {
        return pedidoService.obtenerPedidosPorCliente(clienteId);
    }

    @PostMapping
    public Pedido guardar(@RequestBody Pedido p) {
        return pedidoService.guardarPedido(p);
    }

    @PutMapping("/{id}")
    public Pedido actualizar(@PathVariable Long id, @RequestBody Pedido p) {
        p.setId(id);
        return pedidoService.guardarPedido(p);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
    }
}
