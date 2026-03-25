package com.restaurante.pagos.service;

import com.restaurante.pagos.model.MetodoDePago;
import com.restaurante.pagos.repository.MetodoDePagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetodoDePagoService {

    @Autowired
    private MetodoDePagoRepository repo;

    public List<MetodoDePago> listar() {
        return repo.findAll();
    }

    public MetodoDePago guardar(MetodoDePago metodoDePago) {
        return repo.save(metodoDePago);
    }

    public MetodoDePago actualizar(Long id, MetodoDePago metodoDePago) {
        metodoDePago.setId(id);
        return repo.save(metodoDePago);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
