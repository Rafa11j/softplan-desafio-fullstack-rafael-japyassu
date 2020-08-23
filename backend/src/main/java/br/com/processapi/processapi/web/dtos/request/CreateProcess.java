package br.com.processapi.processapi.web.dtos.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateProcess {

    @NotEmpty(message = "O número do processo não pode ser vazio")
    @NotNull(message = "O número do processo não pode ser nulo")
    private String process;

    @NotEmpty(message = "O nome da vara do processo não pode ser vazio")
    @NotNull(message = "O nome da vara do processo não pode ser nulo")
    private String stick;

    @NotEmpty(message = "O assunto do processo não pode ser vazio")
    @NotNull(message = "O assunto do processo não pode ser nulo")
    private String subject;

    @NotNull(message = "O valor do processo não pode ser nulo")
    private Float value;

    @NotEmpty(message = "O nome do advogado de defesa não pode ser vazio")
    @NotNull(message = "O nome do advogado de defesa não pode ser nulo")
    private String lawyer;

    @NotEmpty(message = "O tipo do processo não pode ser vazio")
    @NotNull(message = "O tipo do processo não pode ser nulo")
    private String processType;

}
