package c09.workflow_management_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "tbl_user")
@Entity
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Username cannot be empty")
    @Size(max = 30, message = "Username cannot have more than 30 character")
    @Pattern(regexp = "^[a-zA-Z ]*$",
            message = "Username cannot contain numbers or special characters")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Email cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "Email must be in correct form")
    private String email;

    private String imagePath = "";

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String description;

    private String activation_key = "";

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean deleted = false;
}
