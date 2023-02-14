package com.example.ad.productos.configuracion;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class MiConfiguracion {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
