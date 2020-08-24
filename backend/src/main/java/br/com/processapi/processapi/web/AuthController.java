package br.com.processapi.processapi.web;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.domain.user.UserRepository;
import br.com.processapi.processapi.security.utils.JwtUtils;
import br.com.processapi.processapi.web.dtos.request.SignIn;
import br.com.processapi.processapi.web.dtos.response.SignInResponse;
import br.com.processapi.processapi.web.dtos.response.UserSignInResponse;
import br.com.processapi.processapi.web.utils.Response;
import br.com.processapi.processapi.web.utils.UriMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = UriMapper.AUTH)
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authentication;

    @PostMapping
    public ResponseEntity<Response<SignInResponse>> login(@Valid @RequestBody SignIn signIn) {
        Response<SignInResponse> response = new Response<>();

        try {
            authentication.authenticate(
                    new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword())
            );
            String token = jwtUtils.generateToken(signIn.getEmail());
            User user = userRepository.findByEmail(signIn.getEmail());

            response.setData(new SignInResponse(token, new UserSignInResponse(user.getName(), user.getEmail(), user.getUserType())));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }

    }
}
