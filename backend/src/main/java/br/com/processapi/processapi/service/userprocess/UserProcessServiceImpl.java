package br.com.processapi.processapi.service.userprocess;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.domain.process.ProcessRepository;
import br.com.processapi.processapi.domain.process.ProcessState;
import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.domain.user.UserRepository;
import br.com.processapi.processapi.domain.user.UserType;
import br.com.processapi.processapi.domain.userprocess.UserProcess;
import br.com.processapi.processapi.domain.userprocess.UserProcessRepository;
import br.com.processapi.processapi.web.dtos.request.AddProcessToUser;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProcessServiceImpl implements UserProcessService{

    private final UserProcessRepository userProcessRepository;
    private final UserRepository userRepository;
    private final ProcessRepository processRepository;

    @Override
    @SneakyThrows
    public UserProcess save(AddProcessToUser addProcessToUser) {
        User user = userRepository.findById(addProcessToUser.getUser()).get();
        Process process = processRepository.findById(addProcessToUser.getProcess()).get();

        if (user.getUserType() != UserType.FINISHER) {
            throw new Exception("Não é possível designar um processo para este tipo de usuário!");
        }

        if (process.getState() == ProcessState.FINISHED) {
            throw new Exception("Não é possível designar este processo, pois ele já foi finalizado!");
        }

        UserProcess userProcess = new UserProcess();
        userProcess.setProcess(process);
        userProcess.setUser(user);

        return userProcessRepository.save(userProcess);
    }
}
