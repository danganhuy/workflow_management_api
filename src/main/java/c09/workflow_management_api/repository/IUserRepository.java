package c09.workflow_management_api.repository;

import c09.workflow_management_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User>  findByUsername(String username);
    Optional<User> findByEmail(String email);
}
