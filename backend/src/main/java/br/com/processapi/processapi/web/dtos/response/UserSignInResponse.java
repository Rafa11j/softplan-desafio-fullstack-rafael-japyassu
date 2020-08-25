package br.com.processapi.processapi.web.dtos.response;

import br.com.processapi.processapi.domain.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInResponse {
    private UUID id;
    private String name;
    private String email;
    private UserType userType;
}
