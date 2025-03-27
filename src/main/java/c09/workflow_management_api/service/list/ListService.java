package c09.workflow_management_api.service.list;

import c09.workflow_management_api.model.Board;
import c09.workflow_management_api.model.List;
import c09.workflow_management_api.model.dto.ListDTO;
import c09.workflow_management_api.repository.IBoardRepository;
import c09.workflow_management_api.repository.IListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ListService implements IListService {
    @Autowired
    private IListRepository iListRepository;
    @Autowired
    private IBoardRepository iBoardRepository;

    @Override
    public java.util.List<List> findAll() {
        return iListRepository.findAll();
    }

    @Override
    public Optional<List> findById(Long id) {
        return iListRepository.findById(id);
    }

    @Override
    public void save(List list) {
        iListRepository.save(list);
    }

    @Override
    public void deleteById(Long id) {
        iListRepository.deleteById(id);
    }

    public List saveList(ListDTO listDTO) {
        Board board = iBoardRepository.findById(listDTO.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + listDTO.getBoardId()));

        List list = new List();
        list.setName(listDTO.getName());
        list.setPriority(listDTO.getPriority());
        list.setBoard(board);

        return iListRepository.save(list);
    }

    public List updateList(ListDTO listDTO) {
        List list = iListRepository.findById(listDTO.getId())
                .orElseThrow(() -> new RuntimeException("List not found with id: " + listDTO.getId()));

        list.setName(listDTO.getName());
        list.setPriority(listDTO.getPriority());

        return iListRepository.save(list);
    }

    public Set<List> findAllByBoardId(Long boardId) {
        //return iListRepository.findAllByBoard_id(boardId);
        return new HashSet<>(iListRepository.findAllByBoard_id(boardId));
    }

}
