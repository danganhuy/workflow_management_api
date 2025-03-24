package c09.workflow_management_api.service.group_member;

import c09.workflow_management_api.model.composite.GroupMemberId;
import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.GroupMember;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.type.EMemberType;
import c09.workflow_management_api.repository.IGroupMemberRepository;
import c09.workflow_management_api.repository.IGroupRepository;
import c09.workflow_management_api.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupMemberService implements IGroupMemberService {
    private final IUserRepository userRepository;
    private final IGroupRepository groupRepository;
    private final IGroupMemberRepository groupMemberRepository;

    @Override
    public List<GroupMember> findAll() {
        return List.of();
    }

    @Override
    public List<GroupMember> findAllByGroupId(Long groupId) {
        return groupMemberRepository.findById_Group_Id(groupId);
    }

    public boolean addMemberByEmail(Long groupId, String email, User requester) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại"));
        if (groupMemberRepository.existsById_GroupAndId_User(group, user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Người dùng đã là thành viên");
        }
        GroupMember requesterMember = groupMemberRepository.findById_GroupAndId_User(group, requester)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền"));
        if (requesterMember.getMember_type() != EMemberType.OWNER && requesterMember.getMember_type() != EMemberType.ADMIN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền");
        }

        GroupMember groupMember = new GroupMember();
        groupMember.setId(new GroupMemberId());
        groupMember.getId().setGroup(group);
        groupMember.getId().setUser(user);
        groupMember.setMember_type(EMemberType.MEMBER);

        groupMemberRepository.save(groupMember);
        return true;
    }

    public void removeMemberById(Long groupId, Long userId, User requester) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại"));
        GroupMember groupMember = groupMemberRepository.findById_GroupAndId_User(group, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không thuộc nhóm"));
        GroupMember requesterMember = groupMemberRepository.findById_GroupAndId_User(group, requester)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền"));
        if (requesterMember.getId() != groupMember.getId() || groupMember.getMember_type() == EMemberType.OWNER) {
            if ((requesterMember.getMember_type() != EMemberType.OWNER && requesterMember.getMember_type() != EMemberType.ADMIN) ||
                    (requesterMember.getMember_type() != EMemberType.OWNER && groupMember.getMember_type() == EMemberType.ADMIN)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền");
            }
        }

        groupMemberRepository.delete(groupMember);
    }

    public void updateMemberRole(Long groupId, Long userId, User requester, EMemberType memberType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại"));
        GroupMember groupMember = groupMemberRepository.findById_GroupAndId_User(group, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không thuộc nhóm"));
        GroupMember requesterMember = groupMemberRepository.findById_GroupAndId_User(group, requester)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền"));
        if (requesterMember.getId() != groupMember.getId() || groupMember.getMember_type() == EMemberType.OWNER) {
            if ((requesterMember.getMember_type() != EMemberType.OWNER && requesterMember.getMember_type() != EMemberType.ADMIN) ||
                    (requesterMember.getMember_type() != EMemberType.OWNER && groupMember.getMember_type() == EMemberType.ADMIN)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền");
            }
        }

        groupMember.setMember_type(memberType);

        groupMemberRepository.save(groupMember);
    }

    public void transferGroupOwnership(Long groupId, Long userId, User requester) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại"));
        GroupMember groupMember = groupMemberRepository.findById_GroupAndId_User(group, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không thuộc nhóm"));
        GroupMember requesterMember = groupMemberRepository.findById_GroupAndId_User(group, requester)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền"));
        if (requesterMember.getMember_type() != EMemberType.OWNER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền");
        }

        groupMember.setMember_type(EMemberType.OWNER);
        groupMemberRepository.save(groupMember);
        requesterMember.setMember_type(EMemberType.ADMIN);
        groupMemberRepository.save(requesterMember);
    }

    @Override
    public Optional<GroupMember> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(GroupMember member) {
    }

    @Override
    public void deleteById(Long id) {
    }
}

