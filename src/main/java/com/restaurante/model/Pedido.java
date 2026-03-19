package com.restaurante.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name="pedido_producto",
            joinColumns=@JoinColumn(name="pedido_id"),
            inverseJoinColumns=@JoinColumn(name="producto_id")
    )
    private List<Producto> productos;

    private LocalDateTime fecha;

    private String estado = "PENDIENTE";

    public Pedido(){
        this.fecha = LocalDateTime.now();
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente(Cliente cliente){
        this.cliente=cliente;
    }

    public List<Producto> getProductos(){
        return productos;
    }

    public void setProductos(List<Producto> productos){
        this.productos=productos;
    }

    public LocalDateTime getFecha(){
        return fecha;
    }

    public void setFecha(LocalDateTime fecha){
        this.fecha=fecha;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado=estado;
    }

}