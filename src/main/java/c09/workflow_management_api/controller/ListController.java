package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.List;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.dto.ListDTO;
import c09.workflow_management_api.service.list.ListService;
import c09.workflow_management_api.util.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lists")
@CrossOrigin("*")
public class ListController {
    private final ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getListsByBoard(@PathVariable Long boardId) {
        Set<ListDTO> listDTOs = listService.findAllByBoardId(boardId).stream().map(ListDTO::new).collect(Collectors.toSet());
        return ResponseEntity.ok(listDTOs);
    }

    @PostMapping
    public ResponseEntity<?> createList(@RequestBody List list, HttpServletRequest request) {
        User user = RequestHandler.getUser(request);
        list.setId(null);
        list.setCreated_by_info(user);
        listService.saveList(list);
        return ResponseEntity.ok("Thêm danh sách thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateList(@PathVariable Long id, @RequestBody List list, HttpServletRequest request) {
        User user = RequestHandler.getUser(request);
        list.setId(id);
        listService.updateListName(list);
        return ResponseEntity.ok("Cập nhập danh sách thành công");
    }

    @PutMapping("/move")
    public ResponseEntity<?> moveList(@RequestBody List list, HttpServletRequest request) {
        User user = RequestHandler.getUser(request);
        listService.moveList(list);
        return ResponseEntity.ok("Di chuyển danh sách thành công");
    }
}


