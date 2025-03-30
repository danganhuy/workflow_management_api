package c09.workflow_management_api.service.label;

import c09.workflow_management_api.model.Label;
import c09.workflow_management_api.model.composite.LabelId;
import c09.workflow_management_api.model.dto.LabelDTO;
import c09.workflow_management_api.service.IGenericService;

import java.util.List;

public interface ILabelService extends IGenericService<Label> {
    List<LabelDTO> findByBoardId(Long boardId);
    Label createLabel(LabelDTO labelDTO, Long boardId);
}
