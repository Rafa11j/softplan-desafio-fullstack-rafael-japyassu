package br.com.processapi.processapi.web;

import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.service.user.UserServiceImpl;
import br.com.processapi.processapi.web.dtos.request.CreateUser;
import br.com.processapi.processapi.web.dtos.request.UpdateUser;
import br.com.processapi.processapi.web.utils.Response;
import br.com.processapi.processapi.web.utils.UriMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<Response<User>> show(@PathVariable("id") UUID id) {
        Response<User> response = new Response<>();
        try {
            response.setData(userService.findById(id));
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

    @PutMapping("/{id}")
    public ResponseEntity<Response<User>> update(@Valid @RequestBody UpdateUser user,@PathVariable("id") UUID id,
                                                 BindingResult result) {
        Response<User> response = new Response<>();
        try {
            response.setData(userService.update(id, user));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity destroy(@PathVariable("id") UUID id) {
        Response<User> response = new Response<>();
        userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
