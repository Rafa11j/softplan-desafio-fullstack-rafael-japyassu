package br.com.processapi.processapi.web.dtos.response;

import br.com.processapi.processapi.domain.process.Process;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProcessResponse {
    private UUID id;
    private Process process;
}
