package br.com.processapi.processapi.domain.userprocess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserProcessRepository extends JpaRepository<UserProcess, UUID> {

    @Query("select up from UserProcess up where up.user_id = :user and process_id = :process")
    UserProcess verifyUserProcessExisting(@Param("user") UUID user, @Param("process") UUID process);
}
