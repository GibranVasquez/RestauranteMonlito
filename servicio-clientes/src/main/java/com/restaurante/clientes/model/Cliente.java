package com.restaurante.clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String telefono;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente(){}

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono=telefono;
    }

    public List<Pedido> getPedidos(){
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos){
        this.pedidos=pedidos;
    }
}
