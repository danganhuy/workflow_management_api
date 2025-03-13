package c09.workflow_management_api.service;

import c09.workflow_management_api.model.DTO.UserDTO;
import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.GroupMember;
import c09.workflow_management_api.model.GroupRole;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.repository.IGroupMemberRepository;
import c09.workflow_management_api.repository.IGroupRepository;
import c09.workflow_management_api.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final IGroupRepository groupRepository;
    private final IUserRepository userRepository;
    private final IGroupMemberRepository groupMemberRepository;

    public List<UserDTO> getGroupMembers(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        return group.getMembers().stream()
                .map(user -> new UserDTO(
                        user.getId(), user.getEmail(), user.getDisplayName(),
                        user.getPhone(), user.getAvatarUrl(), List.of()))
                .collect(Collectors.toList());
    }

    public boolean addMemberByEmail(Long groupId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Nhóm không tồn tại"));

        boolean alreadyMember = groupMemberRepository.existsByGroupAndUser(group, user);
        if (alreadyMember) {
            return false;
        }

        GroupMember groupMember = new GroupMember();
        groupMember.setGroup(group);
        groupMember.setUser(user);
        groupMemberRepository.save(groupMember);

        return true;
    }

    public boolean removeMemberById(Long groupId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Nhóm không tồn tại"));

        GroupMember groupMember = groupMemberRepository.findByGroupAndUser(group, user)
                .orElse(null);

        if (groupMember == null) {
            return false; // Thành viên không thuộc nhóm
        }

        groupMemberRepository.delete(groupMember);
        return true;
    }
    public boolean updateMemberRole(Long groupId, Long userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Nhóm không tồn tại"));

        GroupMember groupMember = groupMemberRepository.findByGroupAndUser(group, user)
                .orElse(null);

        if (groupMember == null) {
            return false; // Thành viên không thuộc nhóm
        }

        // Cập nhật quyền của thành viên
        groupMember.setRole(GroupRole.valueOf(newRole));
        groupMemberRepository.save(groupMember);
        return true;
    }
}

