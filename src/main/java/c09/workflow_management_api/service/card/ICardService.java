package c09.workflow_management_api.service.card;

import c09.workflow_management_api.model.Card;
import c09.workflow_management_api.service.IGenericService;

import java.util.List;

public interface ICardService extends IGenericService<Card> {
    List<Card> findByListId(Long listId);
}
