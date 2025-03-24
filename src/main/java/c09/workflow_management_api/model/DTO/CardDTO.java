package c09.workflow_management_api.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CardDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Integer priority;
    private Long listId;
}
