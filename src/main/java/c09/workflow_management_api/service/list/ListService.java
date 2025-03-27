package c09.workflow_management_api.service.list;

import c09.workflow_management_api.model.Board;
import c09.workflow_management_api.model.List;
import c09.workflow_management_api.repository.IBoardRepository;
import c09.workflow_management_api.repository.IListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ListService implements IListService {
    private final IListRepository listRepository;
    private final IBoardRepository boardRepository;

    public ListService(IListRepository ListRepository, IBoardRepository BoardRepository) {
        this.listRepository = ListRepository;
        this.boardRepository = BoardRepository;
    }

    @Override
    public java.util.List<List> findAll() {
        return listRepository.findAll();
    }

    @Override
    public Optional<List> findById(Long id) {
        return listRepository.findById(id);
    }

    @Override
    public void save(List list) {
        listRepository.save(list);
    }

    @Override
    public void deleteById(Long id) {
        listRepository.deleteById(id);
    }

    public void saveList(List list) {
        Board board = boardRepository.findById(list.getBoard_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        list.setBoard(board);
        list.setDeleted(false);
        listRepository.save(list);
    }

    public void updateListName(List list) {
        List oldList = listRepository.findById(list.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));
        oldList.setName(list.getName());
        listRepository.save(oldList);
    }

    public void moveList(List list) {
        if (!listRepository.existsByIdAndBoard_id(list.getId(), list.getBoard_id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "List not found");
        }

        Set<List> lists = listRepository.findAllByBoard_id(list.getBoard_id());
        java.util.List<List> sortedLists = new ArrayList<>(lists.stream().sorted(Comparator.comparing(list::getPriority)).toList());
        for (int i = 0; i < sortedLists.size(); i++) {
            if (sortedLists.get(i).getId().equals(list.getId())) {
                List movedList = sortedLists.get(i);
                sortedLists.remove(movedList);
                sortedLists.add(list.getPriority(), movedList);
            }
        }
        for (int i = 0; i < sortedLists.size(); i++) {
            sortedLists.get(i).setPriority(i);
        }
        listRepository.saveAll(sortedLists);
    }

    public Set<List> findAllByBoardId(Long boardId) {
        return listRepository.findAllByBoard_id(boardId);
    }

}
