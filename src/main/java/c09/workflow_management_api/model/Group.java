package c09.workflow_management_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "tbl_group")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Enumerated(EnumType.STRING)
    private EAccess access;

    private LocalDateTime created_at;

    private Long created_by;

    private String description;
}
