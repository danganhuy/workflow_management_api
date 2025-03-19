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

    // Tách phương thức tìm toàn bộ bảng và tìm bảng theo nhóm ra làm 2 phương thức
    // Tìm bảng theo nhóm chỉ trả về dữ liệu khi nhóm để công khai hoặc người dùng trong nhóm
    //  ^ Tham khảo phương thức findByIdAndUser của GroupService
    // Phương thức tạo sửa xóa bảng phải kiểm tra người dùng có quyền quản lý nhóm
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
}
