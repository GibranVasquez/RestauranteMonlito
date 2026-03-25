package com.restaurante.entregas.service;

import com.restaurante.entregas.model.Entrega;
import com.restaurante.entregas.repository.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    public List<Entrega> findAll() {
        return entregaRepository.findAll();
    }

    public Optional<Entrega> findById(Long id) {
        return entregaRepository.findById(id);
    }

    public Entrega save(Entrega entrega) {
        return entregaRepository.save(entrega);
    }

    public void deleteById(Long id) {
        entregaRepository.deleteById(id);
    }

    public List<Entrega> findByPedidoId(Long pedidoId) {
        return entregaRepository.findByPedidoId(pedidoId);
    }

    public List<Entrega> findByEstado(String estado) {
        return entregaRepository.findByEstado(estado);
    }

    public Entrega actualizarEstado(Long id, String nuevoEstado) {
        Optional<Entrega> optionalEntrega = entregaRepository.findById(id);
        if (optionalEntrega.isPresent()) {
            Entrega entrega = optionalEntrega.get();
            entrega.setEstado(nuevoEstado);
            if ("ENTREGADO".equalsIgnoreCase(nuevoEstado)) {
                entrega.setFechaEntrega(LocalDateTime.now());
            }
            return entregaRepository.save(entrega);
        }
        return null;
    }
}
