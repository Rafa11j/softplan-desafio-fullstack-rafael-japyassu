package br.com.processapi.processapi.service.userprocess;

import br.com.processapi.processapi.domain.process.Process;
import br.com.processapi.processapi.domain.process.ProcessRepository;
import br.com.processapi.processapi.domain.process.ProcessState;
import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.domain.user.UserRepository;
import br.com.processapi.processapi.domain.user.UserType;
import br.com.processapi.processapi.domain.userprocess.UserProcess;
import br.com.processapi.processapi.domain.userprocess.UserProcessRepository;
import br.com.processapi.processapi.web.dtos.request.AddOpinionProcess;
import br.com.processapi.processapi.web.dtos.request.AddProcessToUser;
import br.com.processapi.processapi.web.dtos.response.ProcessOpinionResponse;
import br.com.processapi.processapi.web.dtos.response.UserProcessResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserProcessServiceImpl implements UserProcessService{

    private final UserProcessRepository userProcessRepository;
    private final UserRepository userRepository;
    private final ProcessRepository processRepository;

    @Override
    public List<UserProcessResponse> findUserProcess(UUID user_id) {
        User user = userRepository.findById(user_id).get();
        List<UserProcess> userProcessList = userProcessRepository.findByUser(user);
        List<UserProcessResponse> response = new ArrayList<>();

        for (UserProcess process: userProcessList) {
            if (process.getProcess().getState() != ProcessState.FINISHED) {
                response.add(new UserProcessResponse(
                        process.getId(), process.getProcess()
                ));
            }
        }
        
        return response;
    }

    @Override
    public List<User> findProcessUsers(UUID process_id) {
        Process process = processRepository.findById(process_id).get();
        List<UserProcess> userProcessList = userProcessRepository.findByProcess(process);
        List<User> users = new ArrayList<>();
        for (UserProcess userProcess: userProcessList) {
            users.add(userProcess.getUser());
        }

        return users;
    }

    @Override
    @SneakyThrows
    public UserProcess save(AddProcessToUser addProcessToUser) {

        User user = userRepository.findById(addProcessToUser.getUser()).get();
        Process process = processRepository.findById(addProcessToUser.getProcess()).get();

        if (userProcessRepository.verifyUserProcessExisting(
                addProcessToUser.getUser(), addProcessToUser.getProcess()
        ) != null) {
            throw new Exception("Processo já designado para este usuário!");
        }

        if (user.getUserType() != UserType.FINISHER) {
            throw new Exception("Não é possível designar um processo para este tipo de usuário!");
        }

        if (process.getState() == ProcessState.FINISHED) {
            throw new Exception("Não é possível designar este processo, pois ele já foi finalizado!");
        }

        UserProcess userProcess = new UserProcess();
        userProcess.setProcess(process);
        userProcess.setUser(user);

        process.setState(ProcessState.IN_PROGRESS);
        processRepository.save(process);

        return userProcessRepository.save(userProcess);
    }

    @Override
    public ProcessOpinionResponse setOpinion(AddOpinionProcess opinionProcess) {
        Process process = processRepository.findById(opinionProcess.getProcess()).get();

        process.setOpinion(opinionProcess.getOpinion());
        process.setState(ProcessState.FINISHED);

        processRepository.save(process);

        return new ProcessOpinionResponse("Processo finalizado com sucesso!");
    }
}
