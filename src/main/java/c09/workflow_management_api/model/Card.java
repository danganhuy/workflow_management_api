package c09.workflow_management_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(insertable = false, updatable = false)
    private Long created_by;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    private User created_by_info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", nullable = false, updatable = false)
    @JsonBackReference
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
