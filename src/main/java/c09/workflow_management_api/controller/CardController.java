package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.dto.CardDTO;
import c09.workflow_management_api.model.Card;
import c09.workflow_management_api.service.card.ICardService;
import c09.workflow_management_api.service.list.IListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
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
    public ResponseEntity<?> createCard(@RequestBody CardDTO cardDTO) {
        Optional<c09.workflow_management_api.model.List> listOptional = listService.findById(cardDTO.getListId());
        if (listOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("List không tồn tại!");
        }

        Card newCard = new Card();
        newCard.setTitle(cardDTO.getTitle());
        newCard.setDescription(cardDTO.getDescription());
        newCard.setDue_date(cardDTO.getDueDate());
        newCard.setPriority(cardDTO.getPriority());
        newCard.setList(listOptional.get());

        cardService.save(newCard);
        return ResponseEntity.ok(newCard);
    }

    @GetMapping("/lists/{listId}/cards")
    public ResponseEntity<?> getCardsByList(@PathVariable Long listId) {
        if (!listService.findById(listId).isPresent()) {
            return ResponseEntity.badRequest().body("List not found!");
        }

        try {
            List<CardDTO> cardDTOs = cardService.findByListId(listId)
                    .stream()
                    .map(card -> {
                        CardDTO dto = new CardDTO();
                        dto.setId(card.getId());
                        dto.setTitle(card.getTitle());
                        dto.setDescription(card.getDescription());
                        dto.setDueDate(card.getDue_date());
                        dto.setPriority(card.getPriority());
                        dto.setListId(card.getList().getId());
                        return dto;
                    })
                    .toList();
            return ResponseEntity.ok(cardDTOs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching cards: " + e.getMessage());
        }
    }
}
