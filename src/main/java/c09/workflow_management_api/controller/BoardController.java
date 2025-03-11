package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.Board;
import c09.workflow_management_api.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/boards")
@CrossOrigin(origins = "http://localhost:5174") // Chinh theo cong React
public class BoardController {
    @Autowired
    private BoardService boardService;
    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        boardService.save(board);
        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }
    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards(){
        return ResponseEntity.ok(boardService.findAll());
    }
}
