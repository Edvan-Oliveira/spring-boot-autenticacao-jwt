package br.com.edvan.autenticacaojwt.filter;

import br.com.edvan.autenticacaojwt.domain.Usuario;
import br.com.edvan.autenticacaojwt.repository.UsuarioRepository;
import br.com.edvan.autenticacaojwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class AutenticaUsuarioTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest requisicao, HttpServletResponse resposta, FilterChain filtro)
            throws ServletException, IOException {

        String token = this.obterTokenDoCabecalhoDaRequisicao(requisicao);
        boolean tokenValido = this.tokenService.validarToken(token);

        if (tokenValido) {
            this.autenticarUsuario(token);
        }

        filtro.doFilter(requisicao, resposta);
    }

    private String obterTokenDoCabecalhoDaRequisicao(HttpServletRequest requisicao) {
        String token = requisicao.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }

    private void autenticarUsuario(String token) {
        Integer idDoUsuario = this.tokenService.obterIdDoUsuario(token);
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(idDoUsuario);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(usuario, null, usuario.getPermissoes());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
