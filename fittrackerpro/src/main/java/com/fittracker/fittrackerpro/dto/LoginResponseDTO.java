package com.fittracker.fittrackerpro.dto;

// DTO simples para ENVIAR o Token e o ID do Usuário
public class LoginResponseDTO {

    private String token;
    private Long usuarioId;

    // Construtor
    public LoginResponseDTO(String token, Long usuarioId) {
        this.token = token;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}