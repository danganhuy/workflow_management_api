package c09.workflow_management_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_list")
@Data
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIgnoreProperties({"board", "cards"})
public class List implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private Long board_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false, updatable = false)
    @JsonBackReference
    private Board board;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(insertable = false, updatable = false)
    private Long created_by;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User created_by_info;

    private Integer priority;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean deleted = false;

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private java.util.List<Card> cards;

    public Integer getPriority(List list) {
        return list.getPriority();
    }
}

