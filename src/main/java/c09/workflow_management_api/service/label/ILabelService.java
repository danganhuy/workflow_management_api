package c09.workflow_management_api.service.label;

import c09.workflow_management_api.model.Label;
import c09.workflow_management_api.model.dtos.LabelDTO;
import c09.workflow_management_api.service.IGenericService;

import java.util.List;

public interface ILabelService extends IGenericService<Label> {
    List<LabelDTO> findByBoardId(Long boardId);
    Label createLabel(LabelDTO labelDTO, Long boardId);
}
