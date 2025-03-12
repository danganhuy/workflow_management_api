//package c09.workflow_management_api.controller;
//
//import c09.workflow_management_api.model.Group;
//import c09.workflow_management_api.model.GroupMember;
//import c09.workflow_management_api.model.GroupRole;
//import c09.workflow_management_api.service.GroupMemberService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@CrossOrigin("*")
//@RestController
//@RequestMapping("/members1")
//public class GroupMemberController {
//    private final GroupMemberService groupMemberService;
//
//    public GroupMemberController(GroupMemberService groupMemberService) {
//        this.groupMemberService = groupMemberService;
//    }
//
//
//    // API lấy danh sách thành viên trong nhóm
//    @GetMapping("/{groupId}")
//    public ResponseEntity<List<GroupMember>> getMembers(@PathVariable Long groupId) {
//        List<GroupMember> members = groupMemberService.getMembersByGroupId(groupId);
//        return ResponseEntity.ok(members);
//    }
//
//    @PostMapping("/{groupId}/add/{userId}")
//    public ResponseEntity<Group> addMember(@PathVariable Long groupId, @PathVariable Long userId) {
//        Group updatedGroup = groupMemberService.addMemberToGroup(groupId, userId);
//        return ResponseEntity.ok(updatedGroup);
//    }
//    // API thêm thành viên vào nhóm bằng email
//    @PostMapping("/{groupId}/add")
//    public ResponseEntity<?> addMemberByEmail(
//            @PathVariable Long groupId,
//            @RequestBody Map<String, String> payload) {
//
//        String email = payload.get("email");
//        String groupRole = payload.getOrDefault("groupRole", "MEMBER"); // Mặc định là MEMBER
//
//        if (email == null || email.isEmpty()) {
//            return ResponseEntity.badRequest().body("Email không hợp lệ");
//        }
//
//        Group updatedGroup = groupMemberService.addMemberByEmail(groupId, email, groupRole);
//        return ResponseEntity.ok(updatedGroup);
//    }
//
//
//
//    // API xóa thành viên khỏi nhóm theo userId
//    @DeleteMapping("/{groupId}/remove/{userId}")
//    public ResponseEntity<Group> removeMember(@PathVariable Long groupId, @PathVariable Long userId) {
//        Group updatedGroup = groupMemberService.removeMember(groupId, userId);
//        return ResponseEntity.ok(updatedGroup);
//    }
//
//    // API đổi quyền của thành viên
//    @PutMapping("/{groupId}/update-role/{userId}")
//    public ResponseEntity<GroupMember> updateMemberRole(
//            @PathVariable Long groupId,
//            @PathVariable Long userId,
//            @RequestBody Map<String, String> payload) { // Nhận JSON từ body
//        String newRole = payload.get("newRole"); // Lấy giá trị role từ request
//        if (newRole == null) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        GroupMember updatedMember = groupMemberService.updateMemberRole(groupId, userId, GroupRole.valueOf(newRole));
//        return ResponseEntity.ok(updatedMember);
//    }
//
//}
