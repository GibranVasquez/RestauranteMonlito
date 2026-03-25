package com.restaurante.productos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @Positive
    private double precio;

    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

    public Producto(){}

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

    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double precio){
        this.precio=precio;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public void setCategoria(Categoria categoria){
        this.categoria=categoria;
    }
}
