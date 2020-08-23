package br.com.processapi.processapi.web.dtos.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUser {

    @NotEmpty(message = "O nome do usuário não pode ser vazio")
    @NotNull(message = "O nome do usuário não pode ser nulo")
    private String name;

    @NotEmpty(message = "O e-mail do usuário não pode ser vazio")
    @NotNull(message = "O e-mail do usuário não pode ser nulo")
    @Email(message = "E-mail inválido")
    private String email;

    @NotEmpty(message = "A tipo do usuário não pode ser vazio")
    @NotNull(message = "A tipo do usuário não pode ser nulo")
    private String userType;

}
