package c09.workflow_management_api.model.dtos;

import c09.workflow_management_api.model.Card;
import c09.workflow_management_api.model.List;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ListDTO {
    private Long id;
    private String name;
    private Long board_id;
    private Long created_by;
    private LocalDateTime created_at;
    private Integer priority;
    private java.util.List<CardDTO> cards;

    public ListDTO(List list) {
        id = list.getId();
        name = list.getName();
        board_id = (list.getBoard() != null) ? list.getBoard().getId() : null;
        created_by = list.getCreated_by();
        created_at = list.getCreated_at();
        priority = list.getPriority();
        if (list.getCards() != null) {
            cards = new java.util.ArrayList<>();
            for (Card card : list.getCards()) {
                cards.add(new CardDTO(card));
            }
        }
    }
}
