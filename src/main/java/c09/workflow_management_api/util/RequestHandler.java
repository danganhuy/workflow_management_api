package c09.workflow_management_api.util;

import c09.workflow_management_api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RequestHandler {
    public static User getUser(HttpServletRequest request) {
        try {
            return (User) request.getAttribute("user");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Chưa đăng nhập");
        }
    }
}
