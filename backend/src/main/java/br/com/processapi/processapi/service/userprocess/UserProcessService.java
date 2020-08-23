package br.com.processapi.processapi.service.userprocess;

import br.com.processapi.processapi.domain.userprocess.UserProcess;
import br.com.processapi.processapi.web.dtos.request.AddProcessToUser;

public interface UserProcessService {
    UserProcess save(AddProcessToUser addProcessToUser);
}
