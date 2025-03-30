package c09.workflow_management_api.model.dto;

import c09.workflow_management_api.model.GroupMember;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.type.EMemberType;
import lombok.Data;

@Data
public class GroupMemberDTO {
    private Long id;
    private String username;
    private String fullname;
    private String email;
    private String imagePath;
    private EMemberType memberType;

    public GroupMemberDTO(GroupMember member) {
        User user = member.getId().getUser();
        id = user.getId();
        username = user.getUsername();
        fullname = user.getFullname();
        email = user.getEmail();
        imagePath = user.getImagePath();
        memberType = member.getMember_type();
    }

}
