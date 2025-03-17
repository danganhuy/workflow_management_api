package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.User;
import c09.workflow_management_api.util.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    @GetMapping
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        User user = RequestHandler.getUser(request);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
