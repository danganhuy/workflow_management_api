package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.dto.JwtResponse;
import c09.workflow_management_api.service.security.JwtService;
import c09.workflow_management_api.service.user.IUserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication authentication
                    = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException(user.getUsername()));
            return ResponseEntity.ok(new JwtResponse(
                    currentUser.getId(), jwt, userDetails.getUsername(),
                    userDetails.getUsername(), userDetails.getAuthorities()));
        } catch (Exception e) {
            return new ResponseEntity<>("Wrong username or password",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            user.setId(null);
            if (userService.findByUsername(user.getUsername()).isPresent()) {
                return new ResponseEntity<>("Username has been used", HttpStatus.BAD_REQUEST);
            }
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                return new ResponseEntity<>("Email has been used", HttpStatus.BAD_REQUEST);
            }
            userService.save(user);
            return ResponseEntity.ok(user);
        } catch (ConstraintViolationException e) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
                errorMessages.add(constraintViolation.getMessage());
            }
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }
    }
}
