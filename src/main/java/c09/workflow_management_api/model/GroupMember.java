package c09.workflow_management_api.model;

import c09.workflow_management_api.model.composite.GroupMemberId;
import c09.workflow_management_api.model.type.EMemberType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Table(name = "tbl_group_member")
@Entity
@Data
public class GroupMember implements Serializable {
    @EmbeddedId
    private GroupMemberId id;

    @Enumerated(EnumType.STRING)
    private EMemberType member_type;
}
