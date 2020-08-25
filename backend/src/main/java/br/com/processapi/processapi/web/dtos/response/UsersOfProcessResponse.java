package br.com.processapi.processapi.web.dtos.response;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersOfProcessResponse {
    private Process process;
    List<User> users;
}
