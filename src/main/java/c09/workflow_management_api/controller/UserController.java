package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.User;
import c09.workflow_management_api.service.security.JwtService;
import c09.workflow_management_api.service.uploadFile.StorageService;
import c09.workflow_management_api.service.user.UserService;
import c09.workflow_management_api.util.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private JwtService jwtService;


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
            @RequestPart("email") String email,
            @RequestPart("description") String description,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {

        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "User not found"));
        }

        User user = optionalUser.get();
        user.setUsername(username);
        user.setEmail(email);
        user.setDescription(description);

        // Nếu có avatar mới thì lưu file và cập nhật đường dẫn
        if (avatar != null && !avatar.isEmpty()) {
            try {
                String fileName = storageService.storeWithUUID(avatar);
                user.setImagePath("/images/" + fileName);   // đổi thành /images để đồng nhất với thư mục
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(Map.of(
                        "message", "Lỗi khi tải lên avatar: " + e.getMessage()
                ));
            }
        }
        userService.save(user);
        user.setPassword(null); // ẩn mật khẩu khi trả về

        return ResponseEntity.ok(Map.of(
                "message", "Cập nhật thông tin thành công",
                "user", user
        ));
    }

}
