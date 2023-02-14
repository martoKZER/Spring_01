package org.iesch.ad.PruebaH2.controlador;

import org.iesch.ad.PruebaH2.model.Persona;
import org.iesch.ad.PruebaH2.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonaControlador {
    @Autowired
    PersonaRepository personaRepository;

    @GetMapping("api/persona")
    public ResponseEntity<?> obtenerTodasPersonas(){
        List<Persona> resultado = personaRepository.findAll();
        if (resultado.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.ok(resultado);
    }
}
