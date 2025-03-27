package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.dto.CardDTO;
import c09.workflow_management_api.model.Card;
import c09.workflow_management_api.service.card.ICardService;
import c09.workflow_management_api.util.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CardController {
    private final ICardService cardService;

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

    @PutMapping("/{listId}/move")
    public ResponseEntity<?> moveCard(@PathVariable Long listId, @RequestBody Card card, HttpServletRequest request) {
        User user = RequestHandler.getUser(request);
        cardService.moveCard(listId, card);
        return ResponseEntity.ok("Di chuyển thẻ thành công");
    }
}
