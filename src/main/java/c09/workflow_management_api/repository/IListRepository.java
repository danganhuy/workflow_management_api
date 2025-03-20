package c09.workflow_management_api.repository;

import c09.workflow_management_api.model.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IListRepository extends JpaRepository<List, Long> {
    Set<List> findAllByBoard_id(Long board_id);
}
