package org.iesch.ad.jwtdemo.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.iesch.ad.jwtdemo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class RestJwtController {
    @Autowired
    JwtService jwtService;

    @GetMapping("/publico/genera")
    public ResponseEntity<?> genera() {
        String jwt = jwtService.createJwt();

        Map<String, String> contenido = new HashMap<>();
        contenido.put("jwt", jwt);
        return ResponseEntity.ok(contenido);
    }

    @GetMapping("/publico/verifica")
    public ResponseEntity<?> verifica(@RequestParam String jwt){
        Jws<Claims> nuestroJwt = jwtService.parseJwt(jwt);
        return ResponseEntity.ok(nuestroJwt);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getMensajeAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Datos del usuario: {}", auth.getPrincipal());
        log.info("Datos de los permisos: {}", auth.getAuthorities());
        log.info("Está autenticado: {}", auth.isAuthenticated());
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("Contenido: ", "Mensaje para fermin el admin");
        return ResponseEntity.ok(mensaje);
    }
}
