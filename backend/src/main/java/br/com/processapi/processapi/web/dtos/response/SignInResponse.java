package br.com.processapi.processapi.web.dtos.response;

import br.com.processapi.processapi.domain.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {

    private String token;
    private UserSignInResponse user;
}
