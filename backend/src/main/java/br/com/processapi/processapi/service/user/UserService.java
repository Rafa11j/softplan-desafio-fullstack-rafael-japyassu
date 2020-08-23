package br.com.processapi.processapi.service.user;

import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.web.dtos.request.CreateUser;
import br.com.processapi.processapi.web.dtos.request.UpdateUser;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();
    Page<User> findAllPaginated(int page, int size);
    User findById(UUID id);
    User save(CreateUser createUser);
    User update(UUID id, UpdateUser updateUser);
    void delete(UUID id);
}
