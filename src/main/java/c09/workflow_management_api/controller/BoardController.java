package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.Board;
import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.service.board.BoardService;
import c09.workflow_management_api.service.group.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/boards")
@CrossOrigin("*")
public class BoardController {
    private final BoardService boardService;

    private final GroupService groupService;

    public BoardController(BoardService boardService, GroupService groupService) {
        this.boardService = boardService;
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        if (board.getGroup() == null || board.getGroup().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<Group> groupOptional = groupService.findById(board.getGroup().getId());
        if (groupOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        board.setGroup(groupOptional.get());
        boardService.save(board);

        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @GetMapping
    public ResponseEntity<List<Board>> getBoards(@RequestParam(required = false) Long groupId) {
        List<Board> boards;
        if (groupId != null) {
            boards = boardService.findByGroupId(groupId);
        } else {
            boards = boardService.findAll();
        }
        return ResponseEntity.ok(boards);
    }

}
