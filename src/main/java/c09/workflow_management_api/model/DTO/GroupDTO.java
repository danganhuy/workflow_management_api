package c09.workflow_management_api.model.dto;

import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.type.EAccess;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupDTO {
    private Long id;
    private String name;
    private String type;
    private EAccess access;
    private LocalDateTime createdAt;
    private Long createdBy;
    private String description;

    public GroupDTO(Group group) {
        id = group.getId();
        name = group.getName();
        type = group.getType();
        access = group.getAccess();
        createdAt = group.getCreated_at();
        createdBy = group.getCreated_by();
        description = group.getDescription();
    }
}