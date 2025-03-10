package c09.workflow_management_api.service.group;

import c09.workflow_management_api.model.Access;
import c09.workflow_management_api.model.Group;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceHuy implements IGroupService {
    private static List<Group> groups;

    static {
        groups = new ArrayList<>();
        groups.add(new Group(1L, "Work", "abc", Access.PUBLIC, "Lorem ipsum dolor sit amet"));
        groups.add(new Group(2L, "Game", "abc", Access.PUBLIC, "Lorem ipsum dolor sit amet"));
        groups.add(new Group(3L, "Grocery", "abc", Access.PUBLIC, "Lorem ipsum dolor sit amet"));
        groups.add(new Group(4L, "Gym", "abc", Access.PUBLIC, "Lorem ipsum dolor sit amet"));
    }

    @Override
    public List<Group> findAll() {
        return groups;
    }

    @Override
    public Optional<Group> findById(Long id) {
        for (Group group : groups) {
            if (group.getId().equals(id)) {
                return Optional.of(group);
            }
        }
        return Optional.empty();
    }

    @Override
    public void save(Group group) {
        if (group.getId() == null) {
            group.setId(groups.size() + 1L);
            groups.add(group);
        }
        else
            groups.set(Math.toIntExact(group.getId() - 1), group);
    }

    @Override
    public void deleteById(Long id) {
        groups.removeIf(group -> group.getId().equals(id));
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setId(i + 1L);
        }
    }
}
