package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.dto.GroupMemberDTO;
import c09.workflow_management_api.model.form.MemberTypeForm;
import c09.workflow_management_api.model.type.EMemberType;
import c09.workflow_management_api.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberGroupController {
    private final MemberService memberService;

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupMembers(@PathVariable Long groupId) {
        List<GroupMemberDTO> members = memberService.getGroupMembers(groupId);
        return ResponseEntity.ok(members);
    }

    @PostMapping("/{groupId}/add")
    public ResponseEntity<?> addMemberByEmail(@PathVariable Long groupId, @RequestParam String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email không được để trống.");
        }

        boolean isAdded = memberService.addMemberByEmail(groupId, email);
        if (isAdded) {
            return ResponseEntity.ok("Thêm thành viên thành công.");
        } else {
            return ResponseEntity.badRequest().body("Không thể thêm thành viên. Email không tồn tại hoặc đã là thành viên.");
        }
    }

    @DeleteMapping("/{groupId}/{userId}")
    public ResponseEntity<?> removeMemberById(@PathVariable Long groupId, @PathVariable Long userId) {
        memberService.removeMemberById(groupId, userId);
        return ResponseEntity.ok("Xóa thành viên thành công.");
    }

    @PutMapping("/{groupId}/{userId}")
    public ResponseEntity<?> updateMemberRole(@PathVariable Long groupId,
                                              @PathVariable Long userId,
                                              @RequestBody MemberTypeForm memberType) {
        EMemberType type;
        try {
            System.out.println(memberType);
            type = EMemberType.valueOf(memberType.getType());
        } catch (Exception e) {
            return new ResponseEntity<>("Vai trò không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        memberService.updateMemberRole(groupId, userId, type);
        return ResponseEntity.ok("Cập nhật quyền thành viên thành công.");
    }
}

