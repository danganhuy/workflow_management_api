package c09.workflow_management_api.model.dto;

import c09.workflow_management_api.model.Card;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CardDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime due_date;
    private Integer priority;
    private Long list_id;

    public CardDTO(Card card) {
        id = card.getId();
        title = card.getTitle();
        description = card.getDescription();
        due_date = card.getDue_date();
        priority = card.getPriority();
        list_id = card.getList().getId();
    }
}
