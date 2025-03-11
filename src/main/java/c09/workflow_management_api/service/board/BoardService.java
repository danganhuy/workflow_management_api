package c09.workflow_management_api.service.board;

import c09.workflow_management_api.model.Board;
import c09.workflow_management_api.repository.board.BoardRepository;
import c09.workflow_management_api.service.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService implements IGenericService<Board> {
    @Autowired
    private BoardRepository boardRepository;

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
}
