package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.List;
import c09.workflow_management_api.model.dto.ListDTO;
import c09.workflow_management_api.service.list.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/lists")
@CrossOrigin("*")
public class ListController {
    @Autowired
    private ListService listService;

    @GetMapping("/board/{boardId}")
    public ResponseEntity<Set<List>> getListsByBoard(@PathVariable  Long boardId) {
        return ResponseEntity.ok(listService.findAllByBoardId(boardId));
    }

    @PostMapping
    public ResponseEntity<List> createList(@RequestBody ListDTO listDTO) {
        if (listDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        List newList = listService.saveList(listDTO);
        return ResponseEntity.ok(newList);
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


