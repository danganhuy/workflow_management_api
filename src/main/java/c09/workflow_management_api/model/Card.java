package c09.workflow_management_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "tbl_card")
@Entity
@Data
public class Card implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, updatable = false)
    private User created_by;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "list_id", nullable = false, updatable = false)
    private List list;

    private Integer priority;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime due_date;

    @ManyToMany(mappedBy = "cards")
    private Set<Label> labels;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean deleted;
}
