package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.DTO.UserDTO;
import c09.workflow_management_api.model.GroupRole;
import c09.workflow_management_api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberGroupController {
    private final MemberService memberService;

    @GetMapping("/{groupId}")
    public ResponseEntity<List<UserDTO>> getGroupMembers(@PathVariable Long groupId) {
        List<UserDTO> members = memberService.getGroupMembers(groupId);
        return ResponseEntity.ok(members);
    }
    @PostMapping("/{groupId}/add")
    public ResponseEntity<String> addMemberByEmail(@PathVariable Long groupId, @RequestBody Map<String, String> request) {
        String email = request.get("email");
        String roleStr = request.get("groupRole");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email không được để trống.");
        }

        // Chuyển đổi chuỗi role sang enum
        GroupRole role;
        try {
            role = GroupRole.valueOf(roleStr.toUpperCase()); // Chuyển thành ENUM
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().body("Vai trò không hợp lệ.");
        }

        boolean isAdded = memberService.addMemberByEmail(groupId, email, role);
        if (isAdded) {
            return ResponseEntity.ok("Thêm thành viên thành công.");
        } else {
            return ResponseEntity.badRequest().body("Không thể thêm thành viên. Email không tồn tại hoặc đã là thành viên.");
        }
    }

    @DeleteMapping("/{groupId}/remove/{userId}")
    public ResponseEntity<String> removeMemberById(@PathVariable Long groupId, @PathVariable Long userId) {
        boolean isRemoved = memberService.removeMemberById(groupId, userId);
        if (isRemoved) {
            return ResponseEntity.ok("Xóa thành viên thành công.");
        } else {
            return ResponseEntity.badRequest().body("Không thể xóa thành viên. Thành viên không tồn tại hoặc không thuộc nhóm.");
        }
    }

    @PutMapping("/{groupId}/update-role/{userId}")
    public ResponseEntity<String> updateMemberRole(@PathVariable Long groupId,
                                                   @PathVariable Long userId,
                                                   @RequestBody Map<String, String> request) {
        String newRole = request.get("role");
        if (newRole == null || newRole.isEmpty()) {
            return ResponseEntity.badRequest().body("Vai trò không được để trống.");
        }

        boolean isUpdated = memberService.updateMemberRole(groupId, userId, newRole);
        if (isUpdated) {
            return ResponseEntity.ok("Cập nhật quyền thành viên thành công.");
        } else {
            return ResponseEntity.badRequest().body("Không thể cập nhật quyền. Thành viên không tồn tại hoặc lỗi xảy ra.");
        }
    }




}

