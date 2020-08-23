package br.com.processapi.processapi.web.dtos.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AddProcessToUser {

    @NotNull(message = "O id processo não pode ser nulo")
    private UUID process;

    @NotNull(message = "O id usuário não pode ser nulo")
    private UUID user;

}
