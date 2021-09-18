package br.com.edvan.autenticacaojwt.resource;

import br.com.edvan.autenticacaojwt.dto.LoginDTO;
import br.com.edvan.autenticacaojwt.dto.TokenDTO;
import br.com.edvan.autenticacaojwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginResource {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha());

        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = this.tokenService.gerarToken(authentication);

        return ResponseEntity.ok(TokenDTO.builder().tipo("Bearer").token(token).build());
    }


}
