package br.com.edvan.autenticacaojwt.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
    private String tipo;
    private String token;
}
