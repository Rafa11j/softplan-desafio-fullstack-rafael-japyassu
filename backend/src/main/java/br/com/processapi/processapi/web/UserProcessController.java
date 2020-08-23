package br.com.processapi.processapi.web;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.domain.userprocess.UserProcess;
import br.com.processapi.processapi.service.user.UserServiceImpl;
import br.com.processapi.processapi.service.userprocess.UserProcessServiceImpl;
import br.com.processapi.processapi.web.dtos.request.AddProcessToUser;
import br.com.processapi.processapi.web.dtos.request.CreateProcess;
import br.com.processapi.processapi.web.utils.Response;
import br.com.processapi.processapi.web.utils.UriMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = UriMapper.USER_PROCESS)
@AllArgsConstructor
public class UserProcessController {

    private final UserProcessServiceImpl userProcessService;

    @PostMapping
    public ResponseEntity<Response<UserProcess>> create(@Valid @RequestBody AddProcessToUser addProcessToUser,
                                                        BindingResult result) {
        Response<UserProcess> response = new Response<>();
        try {
            response.setData(userProcessService.save(addProcessToUser));
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
