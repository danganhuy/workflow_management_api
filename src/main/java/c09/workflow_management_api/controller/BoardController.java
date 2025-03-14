package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.Board;
import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.service.board.BoardService;
import c09.workflow_management_api.service.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/boards")
@CrossOrigin(origins = "http://localhost:5173") // Chỉnh theo cổng React
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private GroupService groupService;

    // ✅ API Tạo Board (có groupId)
    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        System.out.println(" Tạo bảng mới với dữ liệu: " + board);

        // Kiểm tra đối tượng Group có tồn tại không
        if (board.getGroup() == null || board.getGroup().getId() == null) {
            System.out.println(" Lỗi: Thiếu thông tin nhóm (group) trong board!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Kiểm tra nhóm có tồn tại trong cơ sở dữ liệu không
        Optional<Group> groupOptional = groupService.findById(board.getGroup().getId());
        if (groupOptional.isEmpty()) {
            System.out.println(" Lỗi: Không tìm thấy group với ID " + board.getGroup().getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Đặt nhóm vào bảng
        board.setGroup(groupOptional.get());
        boardService.save(board);

        System.out.println("Tạo bảng thành công: " + board);
        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }



    //  API Lấy danh sách Board theo groupId
    @GetMapping
    public ResponseEntity<List<Board>> getBoards(@RequestParam(required = false) Long groupId) {
        System.out.println(" API /api/boards được gọi với groupId: " + groupId);

        List<Board> boards;
        if (groupId != null) {
            System.out.println(" Lọc theo groupId...");
            boards = boardService.findByGroupId(groupId);
        } else {
            System.out.println(" Lấy tất cả bảng...");
            boards = boardService.findAll();
        }

        System.out.println(" Số bảng lấy được: " + boards.size());
        return ResponseEntity.ok(boards);
    }

}
