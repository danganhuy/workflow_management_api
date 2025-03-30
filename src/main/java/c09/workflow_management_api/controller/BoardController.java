package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.Board;
import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.dto.BoardDTO;
import c09.workflow_management_api.service.board.BoardService;
import c09.workflow_management_api.service.group.GroupService;
import c09.workflow_management_api.service.user.UserService;
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
    private final UserService userService;

    public BoardController(BoardService boardService, GroupService groupService, UserService userService) {
        this.boardService = boardService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBoards() {
        List<Board> boards = boardService.findAll();
        List<BoardDTO> boardDTOList = boards.stream().map(BoardDTO::new).toList();
        return ResponseEntity.ok(boardDTOList);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getBoardById(@PathVariable Long id) {
        Optional<Board> board = boardService.findById(id);
        if (board.isPresent()) {
            return ResponseEntity.ok(new BoardDTO(board.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoards(@PathVariable Long id) {
        List<Board> boards;
        if (id != null) {
            boards = boardService.findByGroupId(id);
        } else {
            boards = boardService.findAll();
        }
        List<BoardDTO> boardDTOList = boards.stream().map(BoardDTO::new).toList();
        return ResponseEntity.ok(boardDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoardDetail(@PathVariable Long id) {
        Optional<Board> boardOptional = boardService.findById(id);
        if (boardOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bảng không tồn tại");
        }
        BoardDTO boardDTO = new BoardDTO(boardOptional.get());
        return ResponseEntity.ok(boardDTO);
    }

    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody Board board) {
        Optional<Group> group = groupService.findById(board.getGroup_id());
        if (group.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nhóm không tồn tại");
        }
        Optional<User> user = userService.findById(board.getCreated_by());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
        }

        board.setGroup(group.get());
        board.setCreated_by_info(user.get());
        boardService.save(board);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BoardDTO(board));
    }

    @PutMapping("/name/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable Long id, @RequestBody Board board) {
        Optional<Board> oldBoard = boardService.findById(id);
        if (oldBoard.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bảng không tồn tại");
        }
        oldBoard.get().setName(board.getName());

        boardService.save(oldBoard.get());
        return ResponseEntity.ok(new BoardDTO(board));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
        Optional<Board> boardOptional = boardService.findById(id);
        if (boardOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bảng không tồn tại");
        }
        boardService.deleteById(id);
        return ResponseEntity.ok(id);
    }
}
