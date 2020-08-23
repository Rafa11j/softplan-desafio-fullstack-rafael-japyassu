package br.com.processapi.processapi.domain.process;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProcessRepository extends JpaRepository<Process, UUID> {
    Process findByProcess(String process);

    @Query("select p from Process p where p.state = :state")
    List<Process> findProcessesOpen(@Param("state") String state);
}
