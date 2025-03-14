package c09.workflow_management_api.service.user;

import c09.workflow_management_api.model.User;
import c09.workflow_management_api.service.IGenericService;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService extends IGenericService<User> {
    UserDetails loadUserByUsername(String username);
    User findByUsername(String username);
    User findByEmail(String email);
}
