package c09.workflow_management_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "tbl_notification")
@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean read;
}
