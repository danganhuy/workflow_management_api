package c09.workflow_management_api.service.group_member;

import c09.workflow_management_api.model.composite.GroupMemberId;
import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.GroupMember;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.form.MemberTypeForm;
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
    private final IGroupRepository groupRepository;
    private final IUserRepository userRepository;
    private final IGroupMemberRepository groupMemberRepository;

    @Override
    public List<GroupMember> findAll() {
        return List.of();
    }

    @Override
    public List<GroupMember> findAllByGroupId(Long groupId) {
        return groupMemberRepository.findById_Group_Id(groupId);
    }

    public boolean addMemberByEmail(Long groupId, String email, MemberTypeForm memberTypeForm) {
        // Tìm user và group
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại"));

        // Kiểm tra xem user đã là thành viên chưa
        if (groupMemberRepository.existsById_GroupAndId_User(group, user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Người dùng đã là thành viên");
        }

        // Tạo groupMember mới
        GroupMember groupMember = new GroupMember();
        groupMember.setId(new GroupMemberId());
        groupMember.getId().setGroup(group);
        groupMember.getId().setUser(user);

        // Gán quyền từ memberTypeForm (nếu có)
        EMemberType type = EMemberType.MEMBER; // default
        try {
            type = EMemberType.valueOf(memberTypeForm.getType());
        } catch (Exception e) {
            // Nếu không parse được thì cứ để MEMBER mặc định
        }
        groupMember.setMember_type(type);

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

