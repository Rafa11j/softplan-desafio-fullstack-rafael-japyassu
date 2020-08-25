package br.com.processapi.processapi.service.userprocess;

import br.com.processapi.processapi.domain.user.User;
import br.com.processapi.processapi.domain.userprocess.UserProcess;
import br.com.processapi.processapi.web.dtos.request.AddOpinionProcess;
import br.com.processapi.processapi.web.dtos.request.AddProcessToUser;
import br.com.processapi.processapi.web.dtos.response.ProcessOpinionResponse;
import br.com.processapi.processapi.web.dtos.response.UserProcessResponse;

import java.util.List;
import java.util.UUID;

public interface UserProcessService {
    List<UserProcessResponse> findUserProcess(UUID user_id);
    List<User> findProcessUsers(UUID process_id);
    UserProcess save(AddProcessToUser addProcessToUser);
    ProcessOpinionResponse setOpinion(AddOpinionProcess opinionProcess);
}
