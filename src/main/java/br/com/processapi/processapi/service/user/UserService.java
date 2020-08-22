package br.com.processapi.processapi.service.user;

import br.com.processapi.processapi.domain.user.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();
    User findById(UUID id);
    User save(User user);
}
