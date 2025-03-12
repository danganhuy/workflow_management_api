package c09.workflow_management_api.repository.board;

import c09.workflow_management_api.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 🔹 Lấy danh sách Board theo groupId
    List<Board> findByGroupId(Long groupId);
}
