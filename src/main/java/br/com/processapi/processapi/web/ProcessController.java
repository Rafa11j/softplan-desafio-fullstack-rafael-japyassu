package br.com.processapi.processapi.web;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.service.process.ProcessService;
import br.com.processapi.processapi.web.dtos.request.CreateUser;
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

    @PostMapping
    public ResponseEntity<Response<Process>> create(@Valid @RequestBody Process process, BindingResult result) {
        Response<Process> response = new Response<>();
        try {
            response.setData(processService.save(process));
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
