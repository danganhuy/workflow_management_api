package c09.workflow_management_api.repository;

import c09.workflow_management_api.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICardRepository extends JpaRepository<Card, Long> {
}
