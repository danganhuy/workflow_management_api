package c09.workflow_management_api.service.group;

import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.service.IGenericService;

import java.util.List;

public interface IGroupService extends IGenericService<Group> {
    List<Group> findAllByUser(User user);
    Group findByIdAndUser(Long groupId, User user);
    void save(Group group, User requester);
    void deleteById(Long groupId, User requester);
}
