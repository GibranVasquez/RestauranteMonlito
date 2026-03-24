package com.restaurante.clientes.model;

import jakarta.persistence.*;
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id")
    private Long pedidoId;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getPedidoId(){
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId){
        this.pedidoId = pedidoId;
    }
}
