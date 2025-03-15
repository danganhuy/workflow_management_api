package c09.workflow_management_api.service.board;

import c09.workflow_management_api.model.Board;
import c09.workflow_management_api.repository.board.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService implements IBoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    public void save(Board board) {
        boardRepository.save(board);
    }

    @Override
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    // 🔹 Lọc Board theo groupId
    public List<Board> findByGroupId(Long groupId) {
        return boardRepository.findByGroupId(groupId);
    }
}
