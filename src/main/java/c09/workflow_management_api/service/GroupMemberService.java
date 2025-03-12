package c09.workflow_management_api.service;

import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.GroupMember;
import c09.workflow_management_api.model.GroupRole;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.repository.IGroupMemberRepository;
import c09.workflow_management_api.repository.IGroupRepository;
import c09.workflow_management_api.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberService {
    @Autowired
    private IGroupRepository groupRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IGroupMemberRepository groupMemberRepository;

    public GroupMemberService(IGroupRepository groupRepository, IUserRepository userRepository, IGroupMemberRepository groupMemberRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    // Lấy danh sách thành viên trong nhóm
    public List<GroupMember> getMembersByGroupId(Long groupId) {
        return groupMemberRepository.findByGroupId(groupId);
    }

    public Group addMemberToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!group.getMembers().contains(user)) {
            group.getMembers().add(user);
            groupRepository.save(group);
        }
        return group;
    }

//    public List<User> getMembersByGroupId(Long groupId) {
//        Group group = groupRepository.findById(groupId)
//                .orElseThrow(() -> new RuntimeException("Group not found"));
//        return group.getMembers(); // Trả về danh sách thành viên của nhóm
//    }
    public Group addMemberByEmail(Long groupId, String email, String role) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // Kiểm tra user đã tồn tại chưa
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            // Nếu chưa có, tạo mới
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setDisplayName(email.substring(0, email.indexOf("@"))); // Tạo tên từ email
            return userRepository.save(newUser);
        });

        // Thêm user vào group nếu chưa có
        if (!group.getMembers().contains(user)) {
            group.getMembers().add(user);
            groupRepository.save(group);
        }
        return group;
    }
    // Xóa thành viên khỏi nhóm theo userId
    public Group removeMember(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (group.getMembers().contains(user)) {
            group.getMembers().remove(user);
            groupRepository.save(group);
        }
        return group;
    }

    // Đổi quyền của thành viên
    public GroupMember updateMemberRole(Long groupId, Long userId, GroupRole newRole) {
        GroupMember member = groupMemberRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new RuntimeException("Thành viên không tồn tại trong nhóm"));

        member.setRole(newRole);
        return groupMemberRepository.save(member);
    }
}
