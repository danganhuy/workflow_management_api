package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.dto.CardDTO;
import c09.workflow_management_api.model.Card;
import c09.workflow_management_api.service.card.ICardService;
import c09.workflow_management_api.service.list.IListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CardController {
    private final ICardService cardService;
    private final IListService listService;

    @GetMapping("/cards")
    public ResponseEntity<List<Card>> getAllCards() {
        return ResponseEntity.ok(cardService.findAll());
    }

    @PostMapping("/cards")
    public ResponseEntity<Card> createCard(@RequestBody CardDTO cardDTO) {
        Optional<c09.workflow_management_api.model.List> listOptional = listService.findById(cardDTO.getListId());
        Card newCard = new Card();
        newCard.setTitle(cardDTO.getTitle());
        newCard.setDescription(cardDTO.getDescription());
        newCard.setDue_date(cardDTO.getDueDate());
        newCard.setPriority(cardDTO.getPriority());
        newCard.setList(listOptional.get());
        // Gán listId (giả định đã được validate)
        cardService.save(newCard);
        return ResponseEntity.ok(newCard);
    }

    @GetMapping("/lists/{listId}/cards")
    public ResponseEntity<List<Card>> getCardsByList(@PathVariable Long listId) {
        List<Card> cards = cardService.findByListId(listId);
        return ResponseEntity.ok(cards);
    }
}
