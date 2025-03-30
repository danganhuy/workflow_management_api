package c09.workflow_management_api.service.card;

import c09.workflow_management_api.model.Card;
import c09.workflow_management_api.repository.ICardRepository;
import c09.workflow_management_api.repository.IListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CardService implements ICardService {
    private final ICardRepository cardRepository;
    private final IListRepository listRepository;

    public CardService(ICardRepository cardRepository, IListRepository listRepository) {
        this.cardRepository = cardRepository;
        this.listRepository = listRepository;
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public void save(Card card) {
        c09.workflow_management_api.model.List list = listRepository.findById(card.getList_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        card.setList(list);
        card.setDeleted(false);
        cardRepository.save(card);
    }

    @Override
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }

    @Override
    public void moveCard(Long listId, Card card) {
        c09.workflow_management_api.model.List oldList = listRepository.findById(card.getList_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        c09.workflow_management_api.model.List newList = listRepository.findById(listId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!oldList.getBoard_id().equals(newList.getBoard_id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Card> oldCards = oldList.getCards();
        List<Card> newCards = newList.getCards();

        List<Card> oldSortedCards = new ArrayList<>(oldCards.stream().sorted(Comparator.comparing(card::getPriority)).toList());
        List<Card> newSortedCards = new ArrayList<>(newCards.stream().sorted(Comparator.comparing(card::getPriority)).toList());

        for (int i = 0; i < oldSortedCards.size(); i++) {
            if (oldSortedCards.get(i).getId().equals(card.getId())) {
                Card movedCard;
                if (!oldList.getId().equals(newList.getId())) {
                    movedCard = oldSortedCards.get(i);
                    movedCard.setList(newList);
                    oldSortedCards.remove(movedCard);
                } else {
                    movedCard = newSortedCards.get(i);
                    newSortedCards.remove(movedCard);
                }
                if (card.getPriority() > newSortedCards.size()) {
                    newSortedCards.add(newSortedCards.size(), movedCard);
                }
                else if (card.getPriority() < 0) {
                    newSortedCards.add(0, movedCard);
                }
                else {
                    newSortedCards.add(card.getPriority(), movedCard);
                }
            }
        }

        for (int i = 0; i < newSortedCards.size(); i++) {
            newSortedCards.get(i).setPriority(i);
        }
        cardRepository.saveAll(newSortedCards);

        if (!oldList.getId().equals(newList.getId())) {
            for (int i = 0; i < oldSortedCards.size(); i++) {
                oldSortedCards.get(i).setPriority(i);
            }
            cardRepository.saveAll(oldSortedCards);
        }
    }

    public List<Card> findByListId(Long listId) {
        List<Card> cards = cardRepository.findByListId(listId);
        return cards != null ? new ArrayList<>(cards) : new ArrayList<>();
    }
}
