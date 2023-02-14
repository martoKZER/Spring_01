package com.example.ad.productos.restController;

import com.example.ad.productos.dto.CreateProductoDto;
import com.example.ad.productos.dto.ProductoDto;
import com.example.ad.productos.dto.converter.ProductoDTOConverter;
import com.example.ad.productos.error.ProductoNoEncontradoException;
import com.example.ad.productos.model.Producto;
import com.example.ad.productos.repositorio.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
// Esto de abajo y la linea 18...
@RequiredArgsConstructor
public class ProductoControlador {
    private final ProductoRepository productoRepository;
    private final ProductoDTOConverter productoDTOConverter;
    // Es lo mismo que esto de abajo.
    /*@Autowired
    ProductoRepository productoRepository;*/

    /**
     * Obtener todos los productos
     *
     * @return 404 si no hay productos, 200 y la lista de productos si hay uno o más.
     */
    @GetMapping("api/producto")
    public ResponseEntity<?> obtenerTodos() {
        List<Producto> result = productoRepository.findAll();
        if (result.isEmpty()) {
            // 404
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    Producto createProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Obtener un producto en base a su id
     *
     * @param id
     * @return 200 si la edición tiene exito, 404 si no se encuentra el producto a editar.
     */
    @GetMapping("api/producto/{id}")
    public Producto obtenerUno(@PathVariable Long id) {

        return productoRepository.findById(id).orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    /**
     * Insertamos un nuevo producto
     *
     * @param nuevo a traves de un DTO
     * @return 201 y el producto insertado
     */
    @PostMapping("api/producto")
    public ResponseEntity<?> nuevoProductoDto(@RequestBody CreateProductoDto nuevo) {
        Producto productoNuevo = new Producto();
        productoNuevo.setNombre("a");
        productoNuevo.setPrecio(10);


        return ResponseEntity.ok(productoNuevo);
    }

    /**
     * Editamos un producto
     *
     * @param editar
     * @param id
     * @return 200 Ok si la edicion tiene exito, 404 si no se encuentra el producto a editar.
     */
    @PutMapping("api/producto/{id}")
    public ResponseEntity<?> editarProducto(@RequestBody Producto editar, @PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            return productoRepository.findById(id).map(p -> {
                p.setNombre(editar.getNombre());
                p.setPrecio(editar.getPrecio());
                return ResponseEntity.ok(productoRepository.save(p));
            }).orElseGet(() -> {
                return ResponseEntity.notFound().build();
            });
        }else {
            return ResponseEntity.notFound().build();
        }
        /*if (productoRepository.existsById(id)) {
            editar.setId(id);
            Producto actualizado = productoRepository.save(editar);
            return ResponseEntity.ok(actualizado);
        } else return ResponseEntity.notFound().build();*/

    }

    /**
     * Borra un producto
     *
     * @param id
     * @return
     */
    @DeleteMapping("api/producto/{id}")
    public ResponseEntity<?> borraProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("api/productoDto")
    public ResponseEntity<?> obtenerTodosAtravesDeDto() {
        List<Producto> result = productoRepository.findAll();

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<ProductoDto> listaDTO = result.stream().map(
                    productoDTOConverter::toDto).collect(Collectors.toList());
            return ResponseEntity.ok(listaDTO);
        }
    }

}





