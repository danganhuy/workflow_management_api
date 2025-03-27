package c09.workflow_management_api.repository;

import c09.workflow_management_api.model.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IListRepository extends JpaRepository<List, Long> {
    Set<List> findAllByBoard_id(Long boardId);
    boolean existsByIdAndBoard_id(Long id, Long boardId);
}
