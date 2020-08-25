package br.com.processapi.processapi.service.process;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.domain.process.ProcessRepository;
import br.com.processapi.processapi.domain.process.ProcessState;
import br.com.processapi.processapi.service.userprocess.UserProcessServiceImpl;
import br.com.processapi.processapi.web.dtos.request.AddOpinionProcess;
import br.com.processapi.processapi.web.dtos.request.CreateProcess;
import br.com.processapi.processapi.web.dtos.response.UsersOfProcessResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProcessServiceImpl implements ProcessService{

    private final ProcessRepository processRepository;
    private final UserProcessServiceImpl userProcessService;

    @Override
    public List<Process> findAll() {
//        return processRepository.findProcessesOpen();
        return processRepository.findAll();
    }

    @Override
    public Page<Process> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return processRepository.findAll(pageable);
    }

    @Override
    public Process findById(UUID id) {

        return processRepository.findById(id).get();
    }

    @Override
    public UsersOfProcessResponse findUsersOfProcess(UUID id) {
        UsersOfProcessResponse response = new UsersOfProcessResponse();
        response.setProcess(processRepository.findById(id).get());
        response.setUsers(userProcessService.findProcessUsers(id));
        return response;
    }

    @SneakyThrows
    @Override
    public Process save(CreateProcess createProcess) {
        if (processRepository.findByProcess(createProcess.getProcess()) != null) {
            throw new Exception("Processo já existente");
        }

        Process process = new Process();
        process.setProcess(createProcess.getProcess());
        process.setStick(createProcess.getStick());
        process.setSubject(createProcess.getSubject());
        process.setValue(createProcess.getValue());
        process.setLawyer(createProcess.getLawyer());
        process.setState(ProcessState.OPEN);
        process.setProcessType(createProcess.getProcessType());

        return processRepository.save(process);
    }

    @Override
    public Process setOpinion(AddOpinionProcess opinionProcess) {
        Process process = findById(opinionProcess.getProcess());

        process.setOpinion(opinionProcess.getOpinion());
        process.setState(ProcessState.FINISHED);

        return processRepository.save(process);
    }
}
