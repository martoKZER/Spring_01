package org.iesch.demoSeguridad.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@Slf4j
public class SaludoController {
    @GetMapping("mensaje")
    public ResponseEntity<?> saluda(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Datos de usuario: {}", auth.getPrincipal());
        log.info("Datos de los permisos: {}", auth.getAuthorities());
        log.info("Está autenticado: {}", auth.isAuthenticated());
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("contenido", "Hola mundo");
        return ResponseEntity.ok(mensaje);
    }
}
