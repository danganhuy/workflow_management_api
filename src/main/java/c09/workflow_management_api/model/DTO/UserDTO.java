package c09.workflow_management_api.model.DTO;

import c09.workflow_management_api.model.GroupRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String displayName;
    private String phone;
    private String avatarUrl;

    @JsonProperty("GroupRole")
    private GroupRole groupRole;

}
