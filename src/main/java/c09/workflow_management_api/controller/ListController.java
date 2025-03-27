package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.List;
import c09.workflow_management_api.model.dto.ListDTO;
import c09.workflow_management_api.service.list.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lists")
@CrossOrigin("*")
public class ListController {
    @Autowired
    private ListService listService;

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getListsByBoard(@PathVariable  Long boardId) {
        return ResponseEntity.ok(
                listService.findAllByBoardId(boardId)
                        .stream()
                        .map(ListDTO::fromEntity)
                        .collect(Collectors.toSet())
        );
    }

    @PostMapping
    public ResponseEntity<?> createList(@RequestBody ListDTO listDTO) {
        try {
            if (listDTO.getId() != null) {
                return ResponseEntity.badRequest().body("List mới không được có ID");
            }
            List newList = listService.saveList(listDTO);
            return ResponseEntity.ok(newList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi khi tạo List: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<List> updateList(@PathVariable Long id, @RequestBody ListDTO listDTO) {
        if (!id.equals(listDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }
        List updatedList = listService.updateList(listDTO);
        return ResponseEntity.ok(updatedList);
    }

}


