package c09.workflow_management_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private Long id;
    private String name;
    private String type;
    private Access access;
    private String description;
}
