package br.com.processapi.processapi.domain.process;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessRepository extends JpaRepository<Process, UUID> {
}
