package br.com.processapi.processapi.service.process;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.web.dtos.request.AddOpinionProcess;
import br.com.processapi.processapi.web.dtos.request.CreateProcess;
import br.com.processapi.processapi.web.dtos.response.UsersOfProcessResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProcessService {
    List<Process> findAll();
    Page<Process> findAllPaginated(int page, int size);
    Process findById(UUID id);
    UsersOfProcessResponse findUsersOfProcess(UUID id);
    Process save(CreateProcess createProcess);
    Process setOpinion(AddOpinionProcess opinionProcess);
}
