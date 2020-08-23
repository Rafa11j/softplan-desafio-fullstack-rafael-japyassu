package br.com.processapi.processapi.web.dtos.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AddOpinionProcess {

    @NotEmpty(message = "O parecer não pode ser vazio")
    @NotNull(message = "O parecer não pode ser nulo")
    private String opinion;

    @NotNull(message = "O id processo não pode ser nulo")
    private UUID process;
}
