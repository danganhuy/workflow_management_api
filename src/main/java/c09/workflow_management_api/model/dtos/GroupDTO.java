package c09.workflow_management_api.model.dtos;

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
    private String createdByName;
    private String description;

    public GroupDTO(Group group) {
        id = group.getId();
        name = group.getName();
        type = group.getType();
        access = group.getAccess();
        createdAt = group.getCreated_at();
        createdBy = group.getCreated_by();
        createdByName = group.getCreated_by_info().getUsername();
        description = group.getDescription();
    }
}