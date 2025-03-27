package c09.workflow_management_api.model.dto;

import c09.workflow_management_api.model.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListDTO {
    private Long id;
    private String name;
    private Integer priority;
    private Long boardId;

    public static ListDTO fromEntity(List list) {
        return new ListDTO(
                list.getId(),
                list.getName(),
                list.getPriority(),
                (list.getBoard() != null) ? list.getBoard().getId() : null
        );
    }
}
