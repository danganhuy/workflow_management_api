package c09.workflow_management_api.model.dto;

import c09.workflow_management_api.model.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListDTO {
    private Long id;
    private String name;
    private Long boardId;
    private Integer priority;

    public ListDTO(List list) {
        id = list.getId();
        name = list.getName();
        this.boardId = (list.getBoard() != null) ? list.getBoard().getId() : null;
        priority = list.getPriority();
    }
}
