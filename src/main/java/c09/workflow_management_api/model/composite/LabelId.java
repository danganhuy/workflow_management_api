package c09.workflow_management_api.model.composite;

import c09.workflow_management_api.model.Board;
import c09.workflow_management_api.model.type.EColor;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class LabelId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false, updatable = false)
    private Board board;

    @Enumerated(EnumType.STRING)
    private EColor color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelId that = (LabelId) o;
        return Objects.equals(board, that.board) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board.getId(),color);
    }
}
