package c09.workflow_management_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tbl_user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z ]{1,30}$",
            message = "Username can only have at max 30 characters and doesn't contain numbers or special characters")
    private String username;

    @NotEmpty
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "Email must be in correct form")
    private String email;
}
