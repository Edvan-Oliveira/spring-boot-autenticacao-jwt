package br.com.edvan.autenticacaojwt.service;

import br.com.edvan.autenticacaojwt.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return Optional.ofNullable(this.usuarioRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário [ " + email + " ] não encontrado"));
    }
}
