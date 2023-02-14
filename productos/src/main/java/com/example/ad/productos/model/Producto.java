package com.example.ad.productos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {
    @Id
    Long id;
    String nombre;
    float precio;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
