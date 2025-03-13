package c09.workflow_management_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private String name;

    private Integer position;
}

