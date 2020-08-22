package br.com.processapi.processapi.service.user;

import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.domain.user.UserRepository;
import br.com.processapi.processapi.domain.user.UserType;
import br.com.processapi.processapi.web.dtos.request.CreateUser;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).get();
    }

    @Override
    @SneakyThrows
    public User save(CreateUser createUser) {

        if (userRepository.findByEmail(createUser.getEmail()) != null) {
            System.out.println("E-mail já utilizado!");
            throw new Exception("Este e-mail já está sendo utilizado por outro usuário");
        }

        User user = new User();
        user.setName(createUser.getName());
        user.setEmail(createUser.getEmail());
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        user.setUserType(UserType.valueOf(createUser.getUserType()));
        return userRepository.save(user);
    }
}
