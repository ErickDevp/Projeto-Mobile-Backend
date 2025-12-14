package com.fittracker.fittrackerpro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
	
	@NotBlank(message = "o emial é obrigatorio")
	@Email(message = "o formato do meial e invalido")
	private String email;
	
	@NotBlank(message = "A senha é obrigatória")
	private String senha;
	
	public String getEmail() {
	    return email;
	}

	public void setEmail(String email) {
	    this.email = email;
	}

	public String getSenha() {
	    return senha;
	}

	public void setSenha(String senha) {
	    this.senha = senha;
	}

}
