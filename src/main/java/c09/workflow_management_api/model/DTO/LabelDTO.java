package c09.workflow_management_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelDTO {
    private Long boardId;
    private String color;
    private String title;

//    public LabelDTO(String color, String title) {
//        this.color = color;
//        this.title = title;
//    }
}
