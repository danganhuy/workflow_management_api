package c09.workflow_management_api.service.member;

import c09.workflow_management_api.model.composite.GroupMemberId;
import c09.workflow_management_api.model.dto.GroupMemberDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final IGroupRepository groupRepository;
    private final IUserRepository userRepository;
    private final IGroupMemberRepository groupMemberRepository;

    public List<GroupMemberDTO> getGroupMembers(Long groupId) {
        List<GroupMember> members = groupMemberRepository.findById_Group_Id(groupId);

        return members.stream()
                .map(GroupMemberDTO::new)
                .collect(Collectors.toList());
    }

    public boolean addMemberByEmail(Long groupId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại"));

        if (groupMemberRepository.existsById_GroupAndId_User(group, user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Người dùng đã là thành viên");
        }

        GroupMember groupMember = new GroupMember();
        groupMember.setId(new GroupMemberId());
        groupMember.getId().setGroup(group);
        groupMember.getId().setUser(user);
        groupMemberRepository.save(groupMember);

        return true;
    }

    public void removeMemberById(Long groupId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại"));

        GroupMember groupMember = groupMemberRepository.findById_GroupAndId_User(group, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không thuộc nhóm"));

        groupMemberRepository.delete(groupMember);
    }

    public void updateMemberRole(Long groupId, Long userId, EMemberType memberType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại"));

        GroupMember groupMember = groupMemberRepository.findById_GroupAndId_User(group, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không thuộc nhóm"));

        // Cập nhật quyền của thành viên
        groupMember.setMember_type(memberType);
        groupMemberRepository.save(groupMember);
    }
}

