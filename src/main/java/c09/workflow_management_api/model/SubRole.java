package c09.workflow_management_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sub_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private SubRoleEnum name;
}
