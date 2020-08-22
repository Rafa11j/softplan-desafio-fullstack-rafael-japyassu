package br.com.processapi.processapi.web;

import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.domain.user.UserRepository;
import br.com.processapi.processapi.security.utils.JwtUtils;
import br.com.processapi.processapi.web.dtos.request.SignIn;
import br.com.processapi.processapi.web.dtos.response.SignInResponse;
import br.com.processapi.processapi.web.utils.UriMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = UriMapper.AUTH)
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authentication;

    @PostMapping
    public SignInResponse login(@Valid @RequestBody SignIn signIn) {
        try {
            authentication.authenticate(
                    new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword())
            );

        } catch (Exception e) {
            return new SignInResponse(null, null, null, null);
        }
        String token = jwtUtils.generateToken(signIn.getEmail());
        User user = userRepository.findByEmail(signIn.getEmail());

        return new SignInResponse(token, user.getName(), user.getEmail(), user.getUserType());
    }
}
