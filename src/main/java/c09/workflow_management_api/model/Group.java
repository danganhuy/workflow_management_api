package c09.workflow_management_api.model;

import c09.workflow_management_api.model.type.EAccess;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "tbl_group")
@Entity
@Data
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Enumerated(EnumType.STRING)
    private EAccess access;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(insertable = false, updatable = false)
    private Long created_by;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User created_by_info;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean deleted = false;
}
