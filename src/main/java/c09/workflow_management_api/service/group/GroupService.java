package c09.workflow_management_api.service.group;

import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.GroupMember;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.composite.GroupMemberId;
import c09.workflow_management_api.model.type.EAccess;
import c09.workflow_management_api.model.type.EMemberType;
import c09.workflow_management_api.repository.IGroupMemberRepository;
import c09.workflow_management_api.repository.IGroupRepository;
import c09.workflow_management_api.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IGroupService {
    private final IGroupRepository groupRepository;
    private final IGroupMemberRepository groupMemberRepository;
    private final IUserRepository userRepository;

    public GroupService(IGroupRepository groupRepository, IGroupMemberRepository groupMemberRepository, IUserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public List<Group> findAllByUser(User user) {
        List<GroupMember> groupMembers = groupMemberRepository.findById_User_Id(user.getId());
        List<Group> groups = new ArrayList<>();
        for (GroupMember groupMember : groupMembers) {
            groupRepository.findById(groupMember.getId().getGroup().getId())
                    .filter(group -> !group.getDeleted())
                    .ifPresent(groups::add);
        }
        return groups;
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public Group findByIdAndUser(Long groupId, User user) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
        if (!group.getDeleted() && (groupMemberRepository.existsById_GroupAndId_User(group, user) || group.getAccess() == EAccess.PUBLIC)) {
            return group;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found");
        }
    }

    @Override
    public void save(Group group) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void save(Group group, User requester) {
        if (group.getId() == null) {
            User user = userRepository.findById(group.getCreated_by())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
            group.setCreated_by_info(user);
            Group created = groupRepository.save(group);

            GroupMember member = new GroupMember();
            member.setId(new GroupMemberId());
            member.getId().setGroup(created);
            member.getId().setUser(user);
            member.setMember_type(EMemberType.OWNER);
            groupMemberRepository.save(member);
        }
        else {
            GroupMember member = groupMemberRepository.findById_GroupAndId_User(group, requester)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền"));
            if (member.getMember_type() != EMemberType.OWNER && member.getMember_type() != EMemberType.ADMIN) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền");
            }
            group.setDeleted(false);
            groupRepository.save(group);
        }
    }

    @Override
    public void deleteById(Long groupId, User requester) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
        GroupMember member = groupMemberRepository.findById_GroupAndId_User(group, requester)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền"));
        if (member.getMember_type() != EMemberType.OWNER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền");
        }
    }

    @Override
    public void deleteById(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
        group.setDeleted(true);
        groupRepository.save(group);
    }
}
