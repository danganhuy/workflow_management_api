package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.dto.CardDTO;
import c09.workflow_management_api.model.Card;
import c09.workflow_management_api.service.card.ICardService;
import c09.workflow_management_api.util.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import c09.workflow_management_api.service.card.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin("*")
public class CardController {
    private final ICardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCards() {
        return ResponseEntity.ok(cardService.findAll().stream().map(CardDTO::new).toList());
    }

    @GetMapping("/{listId}")
    public ResponseEntity<?> getCardsByList(@PathVariable Long listId) {
        List<CardDTO> cards = cardService.findByListId(listId).stream().map(CardDTO::new).toList();
        return ResponseEntity.ok(cards);
    }

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody Card card, HttpServletRequest request) {
        User user = RequestHandler.getUser(request);
        card.setId(null);
        card.setCreated_by_info(user);
        card.setDeleted(false);
        cardService.save(card);
        return ResponseEntity.ok("Thêm thẻ thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCard(@PathVariable Long id, @RequestBody Card updatedCard) {
        Optional<Card> cardOptional = cardService.findById(id);
        if (cardOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card không tồn tại");
        }
        Card existingCard = cardOptional.get();
        existingCard.setTitle(updatedCard.getTitle());
        existingCard.setDescription(updatedCard.getDescription());
        existingCard.setList(updatedCard.getList());

        cardService.save(existingCard);
        return ResponseEntity.ok(new CardDTO(existingCard));
    }

    @PutMapping("/{listId}/move")
    public ResponseEntity<?> moveCard(@PathVariable Long listId, @RequestBody Card card, HttpServletRequest request) {
        User user = RequestHandler.getUser(request);
        cardService.moveCard(listId, card);
        return ResponseEntity.ok("Di chuyển thẻ thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        Optional<Card> cardOptional = cardService.findById(id);
        if (cardOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card không tồn tại");
        }
        cardService.deleteById(id);
        return ResponseEntity.ok("Card đã được xóa thành công");
    }
}
