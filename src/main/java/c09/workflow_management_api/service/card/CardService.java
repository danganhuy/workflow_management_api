package c09.workflow_management_api.service.card;

import c09.workflow_management_api.model.Card;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService implements ICardService {
    @Override
    public List<Card> findAll() {
        return List.of();
    }

    @Override
    public Optional<Card> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Card card) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
