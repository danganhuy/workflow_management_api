package c09.workflow_management_api.service.Users;

import c09.workflow_management_api.model.User;
import c09.workflow_management_api.service.IGenericService;

import java.util.Optional;

public interface IUserService extends IGenericService {
    Optional<User> findByEmail(String email);
}
