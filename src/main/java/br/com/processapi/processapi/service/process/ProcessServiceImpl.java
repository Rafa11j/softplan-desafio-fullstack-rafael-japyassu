package br.com.processapi.processapi.service.process;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.domain.process.ProcessRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProcessServiceImpl implements ProcessService{

    private final ProcessRepository processRepository;

    @Override
    public List<Process> findAll() {
        return processRepository.findAll();
    }

    @Override
    public Process findById(UUID id) {
        return processRepository.findById(id).get();
    }

    @Override
    public Process save(Process process) {
        return processRepository.save(process);
    }
}
