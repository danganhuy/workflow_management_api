package c09.workflow_management_api.model.dtos;

import c09.workflow_management_api.model.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {
    private Long id;
    private String name;
    private Long groupId;
    private LocalDateTime created_at;
    private Long created_by;
    private String description;

    public BoardDTO(Board board) {
        id = board.getId();
        name = board.getName();
        groupId = board.getGroup_id();
        created_at = board.getCreated_at();
        created_by = board.getCreated_by();
        description = board.getDescription();
    }
}
