package br.com.edvan.autenticacaojwt.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteResource {

    @GetMapping
    public ResponseEntity<String> autenticado() {
        return ResponseEntity.ok("Olá, mundo!");
    }

    @GetMapping(path = "/administrador/tela")
    public ResponseEntity<String> administrador() {
        return ResponseEntity.ok("Olá, ROLE_ADMINISTRADOR");
    }

    @GetMapping(path = "/usuario/tela")
    public ResponseEntity<String> usuario() {
        return ResponseEntity.ok("Olá, ROLE_USUARIO");
    }
}
