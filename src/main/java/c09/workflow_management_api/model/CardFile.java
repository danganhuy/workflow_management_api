package c09.workflow_management_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Table(name = "tbl_card_file")
@Entity
@Data
public class CardFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private Long size;

    private String filePath;

    @Column(insertable = false, updatable = false)
    private Long card_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;
}
