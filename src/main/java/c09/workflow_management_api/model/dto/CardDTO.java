package c09.workflow_management_api.model.dto;

import c09.workflow_management_api.model.Card;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CardDTO {
    private Long id;
    private String title;
    private Integer priority;
    private String createdByName;
    private String description;
    private LocalDateTime created_at;
    private LocalDateTime due_date;


    public CardDTO(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.priority = card.getPriority();
        this.createdByName = card.getCreated_by_info().getUsername();
        this.description = card.getDescription();
        this.created_at = card.getCreated_at();
        this.due_date = card.getDue_date();
    }
}
