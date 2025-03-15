package c09.workflow_management_api.service.group;

import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.repository.IGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IGroupService {
    private final IGroupRepository groupRepository;

    public GroupService(IGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
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
        groupRepository.save(group);
    }

    @Override
    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }
}
