package br.com.processapi.processapi.service.user;

import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.domain.user.UserRepository;
import br.com.processapi.processapi.domain.user.UserType;
import br.com.processapi.processapi.web.dtos.request.CreateUser;
import br.com.processapi.processapi.web.dtos.request.UpdateUser;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Page<User> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(0, 10);
        return userRepository.findAll(pageable);
    }

    @Override
    @SneakyThrows
    public User findById(UUID id) {

        User user = userRepository.findById(id).get();
        if (user == null) {
            throw new Exception("Usuário não encontrado!");
        }

        return user;
    }

    @Override
    @SneakyThrows
    public User save(CreateUser createUser) {

        if (userRepository.findByEmail(createUser.getEmail()) != null) {
            throw new Exception("Este e-mail já está sendo utilizado por outro usuário");
        }

        User user = new User();
        user.setName(createUser.getName());
        user.setEmail(createUser.getEmail());
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        user.setUserType(UserType.valueOf(createUser.getUserType()));
        return userRepository.save(user);
    }

    @Override
    @SneakyThrows
    public User update(UUID id, UpdateUser updateUser) {
        User findUser = userRepository.findByEmail(updateUser.getEmail());
        if (findUser != null && !updateUser.getEmail().equals(findUser.getEmail())) {
            throw new Exception("Este e-mail já está sendo utilizado por outro usuário");
        }
        User user = new User();
        user.setId(id);
        user.setName(updateUser.getName());
        user.setEmail(updateUser.getEmail());
        user.setPassword(findUser.getPassword());
        user.setUserType(UserType.valueOf(updateUser.getUserType()));
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
