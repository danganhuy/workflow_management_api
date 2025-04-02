package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.dtos.ChangePasswordRequest;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.service.uploadFile.StorageService;
import c09.workflow_management_api.service.user.UserService;
import c09.workflow_management_api.util.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final StorageService storageService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, StorageService storageService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.storageService = storageService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        User user = RequestHandler.getUser(request);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);
        User user = optionalUser.get();
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestPart("username") String username,
            @RequestPart("fullname") String fullname,
            @RequestPart("email") String email,
            @RequestPart("description") String description,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {

        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "User not found"));
        }

        User user = optionalUser.get();
        user.setUsername(username);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setDescription(description);

        if (avatar != null && !avatar.isEmpty()) {
            try {
                String fileName = storageService.storeWithUUID(avatar);
                user.setImagePath(fileName);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(Map.of(
                        "message", "Lỗi khi tải lên avatar: " + e.getMessage()
                ));
            }
        }
        userService.update(user);
        return ResponseEntity.ok(Map.of(
                "message", "Cập nhật thông tin thành công",
                "user", user
        ));
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "User không tồn tại."));
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Mật khẩu hiện tại không đúng."));
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Mật khẩu mới và xác nhận không khớp."));
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Mật khẩu mới không được trùng với mật khẩu hiện tại."));
        }

        userService.changePassword(id, request.getNewPassword());

        return ResponseEntity.ok(Map.of("message", "Thay đổi mật khẩu thành công."));
    }

}
