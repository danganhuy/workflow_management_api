package c09.workflow_management_api.repository;

import c09.workflow_management_api.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.list.id = :listId")
    List<Card> findByListId(@Param("listId") Long listId);
}
