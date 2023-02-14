package com.example.ad.productos.dto.converter;

import com.example.ad.productos.dto.ProductoDto;
import com.example.ad.productos.model.Producto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // tiene que ser final lo que haya aquí dentro!!
public class ProductoDTOConverter {
    private final ModelMapper modelMapper;

    public ProductoDto toDto(Producto producto){
        return modelMapper.map(producto, ProductoDto.class);
    }
}
