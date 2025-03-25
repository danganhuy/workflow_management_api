package c09.workflow_management_api.service.card;

import c09.workflow_management_api.model.Card;
import c09.workflow_management_api.repository.ICardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService implements ICardService {
    private final ICardRepository cardRepository;

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
        cardRepository.save(card);
    }

    @Override
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }

    public List<Card> findByListId(Long listId) {
        return cardRepository.findByListId(listId);
    }
}
