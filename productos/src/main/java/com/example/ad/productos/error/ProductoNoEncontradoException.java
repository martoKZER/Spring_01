package com.example.ad.productos.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductoNoEncontradoException extends RuntimeException{
    private static final long serialVersionUID = 3432946238954L;
    public ProductoNoEncontradoException(Long id){
        super("No se puede encontrar el producto." + id);
        System.out.println("llega");
    }

}
