package c09.workflow_management_api.repository;

import c09.workflow_management_api.model.Label;
import c09.workflow_management_api.model.composite.LabelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ILabelRepository extends JpaRepository<Label, LabelId> {
    @Query("SELECT l FROM Label l WHERE l.id.boardId = :boardId")
    List<Label> findByBoardId(@Param("boardId") Long boardId);
}
