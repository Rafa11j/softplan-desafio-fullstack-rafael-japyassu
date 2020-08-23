package br.com.processapi.processapi.domain.userprocess;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.web.dtos.response.UserProcessResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface UserProcessRepository extends JpaRepository<UserProcess, UUID> {

    @Query("select up from UserProcess up where up.user.id = :user and up.process.id = :process")
    UserProcess verifyUserProcessExisting(@Param("user") UUID user, @Param("process") UUID process);

//    @Query("select up.process from UserProcess up where up.user.id = :user")
    List<UserProcess> findByUser(User user);
}
