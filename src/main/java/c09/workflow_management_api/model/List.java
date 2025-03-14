package c09.workflow_management_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "tbl_list")
@Data
public class List implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false, updatable = false)
    private Board board;

    @Column(nullable = false)
    private String name;

    private Integer priority;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean deleted;
}

