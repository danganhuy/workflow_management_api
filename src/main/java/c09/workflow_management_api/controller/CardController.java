package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.Card;
import c09.workflow_management_api.model.dto.CardDTO;
import c09.workflow_management_api.service.card.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/cards")
@CrossOrigin("*")
public class CardController {
    private  final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCard(){
        List<Card> cards = cardService.findAll();
        List<CardDTO> cardsDTO = cards.stream().map(CardDTO::new).toList();
        return ResponseEntity.ok(cardsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCardDetails(@PathVariable Long id){
        Optional<Card> cardOptional = cardService.findById(id);
        if (cardOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bảng không tồn tại");
        }
        CardDTO cardDTO = new CardDTO(cardOptional.get());
        return ResponseEntity.ok(cardDTO);
    }

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody Card card) {
        if (card.getList() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("List của card không được để trống");
        }
        cardService.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CardDTO(card));
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
