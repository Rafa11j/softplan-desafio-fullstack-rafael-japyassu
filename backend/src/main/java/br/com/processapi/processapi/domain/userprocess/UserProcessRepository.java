package br.com.processapi.processapi.domain.userprocess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserProcessRepository extends JpaRepository<UserProcess, UUID> {
}
