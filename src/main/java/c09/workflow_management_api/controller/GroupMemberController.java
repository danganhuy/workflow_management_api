package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.GroupMember;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.dto.GroupMemberDTO;
import c09.workflow_management_api.model.form.MemberTypeForm;
import c09.workflow_management_api.model.type.EMemberType;
import c09.workflow_management_api.service.group_member.GroupMemberService;
import c09.workflow_management_api.util.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class GroupMemberController {
    private final GroupMemberService groupMemberService;

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupMembers(@PathVariable Long groupId) {
        List<GroupMember> memberList = groupMemberService.findAllByGroupId(groupId);
        List<GroupMemberDTO> memberDTOList = memberList.stream().map(GroupMemberDTO::new).toList();
        return ResponseEntity.ok(memberDTOList);
    }

    @PostMapping("/{groupId}")
    public ResponseEntity<?> addMemberByEmail(@PathVariable Long groupId, @RequestParam String email, HttpServletRequest request) {
        User requester = RequestHandler.getUser(request);
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email không được để trống.");
        }

        boolean isAdded = groupMemberService.addMemberByEmail(groupId, email, requester);
        if (isAdded) {
            return ResponseEntity.ok("Thêm thành viên thành công.");
        } else {
            return ResponseEntity.badRequest().body("Không thể thêm thành viên. Email không tồn tại hoặc đã là thành viên.");
        }
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> leaveGroup (@PathVariable Long groupId, HttpServletRequest request) {
        User requester = RequestHandler.getUser(request);
        groupMemberService.removeMemberById(groupId, requester.getId(), requester);
        return ResponseEntity.ok("Rời khỏi nhóm thành công");
    }

    @DeleteMapping("/{groupId}/{userId}")
    public ResponseEntity<?> removeMemberById(@PathVariable Long groupId, @PathVariable Long userId,
                                              HttpServletRequest request) {
        User requester = RequestHandler.getUser(request);
        groupMemberService.removeMemberById(groupId, userId, requester);
        return ResponseEntity.ok("Xóa thành viên thành công.");
    }

    @PutMapping("/{groupId}/{userId}")
    public ResponseEntity<?> updateMemberRole(@PathVariable Long groupId, @PathVariable Long userId,
                                              @RequestBody MemberTypeForm memberType, HttpServletRequest request) {
        User requester = RequestHandler.getUser(request);
        EMemberType type;
        try {
            type = EMemberType.valueOf(memberType.getType());
        } catch (Exception e) {
            return new ResponseEntity<>("Vai trò không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        groupMemberService.updateMemberRole(groupId, userId, requester, type);
        return ResponseEntity.ok("Cập nhật quyền thành viên thành công.");
    }

    @PutMapping("/{groupId}/{userId}/owner")
    public ResponseEntity<?> transferGroupOwnership(@PathVariable Long groupId, @PathVariable Long userId,
                                              HttpServletRequest request) {
        User requester = RequestHandler.getUser(request);
        groupMemberService.transferGroupOwnership(groupId, userId, requester);
        return ResponseEntity.ok("Chuyển quyền sở hữu nhóm thành công");
    }
}

