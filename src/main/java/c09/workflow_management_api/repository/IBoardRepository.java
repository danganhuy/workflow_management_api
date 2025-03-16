package c09.workflow_management_api.repository;

import c09.workflow_management_api.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByGroupId(Long groupId);
}
