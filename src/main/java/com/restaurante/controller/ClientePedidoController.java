package com.restaurante.controller;

import com.restaurante.model.Cliente;
import com.restaurante.model.Pedido;
import com.restaurante.service.ClienteService;
import com.restaurante.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clientes/{clienteId}/pedidos")
public class ClientePedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Pedido> listarPorCliente(@PathVariable Long clienteId) {
        validarCliente(clienteId);
        return pedidoService.listarPorCliente(clienteId);
    }

    @GetMapping("/{pedidoId}")
    public Pedido obtenerPorCliente(@PathVariable Long clienteId, @PathVariable Long pedidoId) {
        validarCliente(clienteId);
        return pedidoService.obtenerPorCliente(clienteId, pedidoId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido no encontrado para el cliente"));
    }

    @PostMapping
    public Pedido crearParaCliente(@PathVariable Long clienteId, @RequestBody Pedido pedido) {
        Cliente cliente = validarCliente(clienteId);
        pedido.setCliente(cliente);
        return pedidoService.guardar(pedido);
    }

    private Cliente validarCliente(Long clienteId) {
        return clienteService.obtenerPorId(clienteId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente no encontrado"));
    }
}
