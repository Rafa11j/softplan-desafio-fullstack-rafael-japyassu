package br.com.processapi.processapi.service.process;

import br.com.processapi.processapi.domain.process.Process;

import java.util.List;
import java.util.UUID;

public interface ProcessService {
    List<Process> findAll();
    Process findById(UUID id);
    Process save(Process process);
}
