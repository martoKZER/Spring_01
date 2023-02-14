package com.example.ad.productos.restController;

import com.example.ad.productos.model.Categoria;
import com.example.ad.productos.repositorio.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Esto de abajo y la linea 18...
@RequiredArgsConstructor
public class CategoriaControlador {

    private final CategoriaRepository categoriaRepository;
    // Es lo mismo que esto de abajo.
    /*@Autowired
    CategoriaRepository categoriaRepository;*/

    /**
     * Obtener todos los categorias
     *
     * @return 404 si no hay categorias, 200 y la lista de categorias si hay uno o más.
     */
    @GetMapping("api/categoria")
    public ResponseEntity<?> obtenerTodos() {
        List<Categoria> result = categoriaRepository.findAll();
        if (result.isEmpty()) {
            // 404
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    Categoria createCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    /**
     * Obtener un categoria en base a su id
     *
     * @param id
     * @return Null si no se encuentra el categoria.
     */
    @GetMapping("api/categoria/{id}")
    public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
        Categoria result = categoriaRepository.findById(id).orElse(null);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Insertamos un nuevo categoria
     *
     * @param nuevo
     * @return 201 y el categoria insertado
     */
    @PostMapping("api/categoria")
    public ResponseEntity<?> nuevoCategoria(@RequestBody Categoria nuevo) {
        Categoria saved = categoriaRepository.save(nuevo);
        // Codigo 201
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Editamos un categoria
     *
     * @param editar
     * @param id
     * @return 200 Ok si la edicion tiene exito, 404 si no se encuentra el categoria a editar.
     */
    @PutMapping("api/categoria/{id}")
    public ResponseEntity<?> editarCategoria(@RequestBody Categoria editar, @PathVariable Long id) {
        /*if (categoriaRepository.existsById(id)) {
            return categoriaRepository.findById(id).map(p -> {
                p.setNombre(editar.getNombre());
                p.setPrecio(editar.getPrecio());
                return ResponseEntity.ok(categoriaRepository.save(p));
            }).orElseGet(() -> {
                return ResponseEntity.notFound().build();
            });
        }*/
        if (categoriaRepository.existsById(id)){
            editar.setId(id);
            Categoria actualizado = categoriaRepository.save(editar);
            return ResponseEntity.ok(actualizado);
        }else return ResponseEntity.notFound().build();

    }

    /**
     * Borra una categoria.
     * @param id
     * @return
     */
    @DeleteMapping("api/categoria/{id}")
    public ResponseEntity<?> borraCategoria(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
