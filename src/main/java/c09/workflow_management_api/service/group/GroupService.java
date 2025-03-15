package c09.workflow_management_api.service.group;

import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.repository.IGroupRepository;
import c09.workflow_management_api.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IGroupService {
    private final IGroupRepository groupRepository;
    private final IUserRepository userRepository;

    public GroupService(IGroupRepository groupRepository, IUserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public void save(Group group) {
        User user = userRepository.findById(group.getCreated_by())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
        group.setCreated_by_info(user);
        groupRepository.save(group);
    }

    @Override
    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }
}
