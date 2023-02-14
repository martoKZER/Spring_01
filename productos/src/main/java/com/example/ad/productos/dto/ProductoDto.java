package com.example.ad.productos.dto;

import com.example.ad.productos.model.Categoria;
import lombok.Data;
@Data
public class ProductoDto {
    private Long id;
    private String nombre;
    private float precio;
    private Categoria categoria;
}
