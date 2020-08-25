package br.com.processapi.processapi.web;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.service.process.ProcessService;
import br.com.processapi.processapi.web.dtos.request.CreateProcess;
import br.com.processapi.processapi.web.dtos.request.CreateUser;
import br.com.processapi.processapi.web.dtos.response.UsersOfProcessResponse;
import br.com.processapi.processapi.web.utils.Response;
import br.com.processapi.processapi.web.utils.UriMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = UriMapper.PROCESS)
@AllArgsConstructor
public class ProcessController {

    private final ProcessService processService;

    @GetMapping
    public ResponseEntity<Response<List<Process>>> index() {
        Response<List<Process>> response = new Response<>();
        try {
            response.setData(processService.findAll());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Process>> show(@PathVariable(name = "id") UUID id) {
        Response<Process> response = new Response<>();
        try {
            response.setData(processService.findById(id));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<Response<UsersOfProcessResponse>> showUsers(@PathVariable(name = "id") UUID id) {
        Response<UsersOfProcessResponse> response = new Response<>();
        try {
            response.setData(processService.findUsersOfProcess(id));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<Response<Page<Process>>> paginated(@RequestParam(name = "page") int page,
                                                             @RequestParam(name = "size") int size) {
        Response<Page<Process>> response = new Response<>();
        try {
            response.setData(processService.findAllPaginated(page, size));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Response<Process>> create(@Valid @RequestBody CreateProcess createProcess, BindingResult result) {
        Response<Process> response = new Response<>();
        try {
            response.setData(processService.save(createProcess));
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            if (e.getMessage().equals("Processo já existente")) {
                errors.add(e.getMessage());
            }
            result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
