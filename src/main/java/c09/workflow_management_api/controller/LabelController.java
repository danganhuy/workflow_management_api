package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.Label;
import c09.workflow_management_api.model.dtos.LabelDTO;
import c09.workflow_management_api.service.label.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/labels")
@CrossOrigin("*")
public class LabelController {
    @Autowired
    private ILabelService labelService;

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getLabelsByBoardId(@PathVariable Long boardId) {
        List<LabelDTO> labels = labelService.findByBoardId(boardId);
        return ResponseEntity.ok(labels);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addLabel(@RequestBody LabelDTO labelDTO) {
        try {
            if (labelDTO.getBoardId() == null) {
                return ResponseEntity.badRequest().body("Board ID is required!");
            }

            Label label = labelService.createLabel(labelDTO, labelDTO.getBoardId());

            return ResponseEntity.ok(new LabelDTO(label.getId().getBoardId(),
                    label.getId().getColor().toString(),
                    label.getTitle()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding label: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding label: " + e.getMessage());
        }
    }
}
