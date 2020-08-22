package br.com.processapi.processapi.web;

import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.service.user.UserServiceImpl;
import br.com.processapi.processapi.web.dtos.request.CreateUser;
import br.com.processapi.processapi.web.utils.Response;
import br.com.processapi.processapi.web.utils.UriMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = UriMapper.USER)
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    @PreAuthorize("@processSecurityService.hasPermissionAdministrator(authentication)")
    public ResponseEntity<Response<List<User>>> index() {
        Response<List<User>> response = new Response<>();
        try {
            response.setData(userService.findAll());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Response<User>> create(@Valid @RequestBody CreateUser createUser, BindingResult result) {
        Response<User> response = new Response<>();
        try {
            User user = userService.save(createUser);
            response.setData(user);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
