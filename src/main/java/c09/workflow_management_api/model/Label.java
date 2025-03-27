package c09.workflow_management_api.model;

import c09.workflow_management_api.model.composite.LabelId;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Table(name = "tbl_label")
@Entity
@Data
public class Label implements Serializable {
    @EmbeddedId
    private LabelId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", insertable = false, updatable = false)
    private Board board;
    @ManyToMany
    @JoinTable(name = "tbl_card_label",
            joinColumns = {
                    @JoinColumn(name = "board_id", referencedColumnName = "board_id"),
                    @JoinColumn(name = "color", referencedColumnName = "color")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "card_id", referencedColumnName = "id")
            }
    )
    private Set<Card> cards;

    private String title;
}
