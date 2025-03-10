package c09.workflow_management_api.repository;

import c09.workflow_management_api.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupRepository extends JpaRepository<Group, Long> {
}
