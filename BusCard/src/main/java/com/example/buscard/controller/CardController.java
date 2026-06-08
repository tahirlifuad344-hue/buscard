package com.example.buscard.controller;

import com.example.buscard.dto.request.CardCreateRequest;
import com.example.buscard.dto.request.CardDeleteRequest;
import com.example.buscard.dto.request.CardUpdateRequest;
import com.example.buscard.dto.response.CardCreateResponse;
import com.example.buscard.dto.response.CardDeleteResponse;
import com.example.buscard.dto.response.CardReadResponse;
import com.example.buscard.dto.response.CardUpdateResponse;
import com.example.buscard.entity.Card;
import com.example.buscard.entity.Trip;
import com.example.buscard.mapper.CardMapper;
import com.example.buscard.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final CardMapper cardMapper;

    // Yeni kart yaratmaq
    @PostMapping
    public ResponseEntity<CardCreateResponse> createCard(@Valid @RequestBody CardCreateRequest request) {
        Card card = cardService.createCard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardMapper.toCreateResponse(card));
    }

    // Kartı ID-yə görə oxumaq
    @GetMapping("/{id}")
    public ResponseEntity<CardReadResponse> getCardById(@PathVariable Long id) {
        Card card = cardService.getCardById(id);
        return ResponseEntity.ok(cardMapper.toReadResponse(card));
    }

    // Bütün kartları oxumaq
    @GetMapping
    public ResponseEntity<List<CardReadResponse>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        List<CardReadResponse> responses = cards.stream()
                .map(cardMapper::toReadResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // Kart nömrəsinə görə oxumaq
    @GetMapping("/number/{cardNumber}")
    public ResponseEntity<CardReadResponse> getCardByCardNumber(@PathVariable Long cardNumber) {
        Card card = cardService.getCardByCardNumber(cardNumber);
        return ResponseEntity.ok(cardMapper.toReadResponse(card));
    }

    // Kartı yeniləmək
    @PutMapping("/{id}")
    public ResponseEntity<CardUpdateResponse> updateCard(
            @PathVariable Long id,
            @Valid @RequestBody CardUpdateRequest request) {
        Card card = cardService.updateCard(id, request.getBalance());
        return ResponseEntity.ok(cardMapper.toUpdateResponse(card));
    }

    // Kartı silmək
    @DeleteMapping("/{id}")
    public ResponseEntity<CardDeleteResponse> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok(cardMapper.toDeleteResponse(id, "Kart uğurla silindi"));
    }

    // Balans artırmaq
    @PostMapping("/{id}/increase")
    public ResponseEntity<CardUpdateResponse> increaseBalance(
            @PathVariable Long id,
            @RequestParam Long amount) {
        Card card = cardService.increaseBalance(id, amount);
        return ResponseEntity.ok(cardMapper.toUpdateResponse(card));
    }

    // Səfər tarixçəsini göstərmək
    @GetMapping("/{id}/trips")
    public ResponseEntity<List<Trip>> getTripHistory(@PathVariable Long id) {
        List<Trip> trips = cardService.getTripHistory(id);
        return ResponseEntity.ok(trips);
    }

    // Aylıq xərcləri hesablamaq
    @GetMapping("/{id}/expenses")
    public ResponseEntity<BigDecimal> calculateMonthlyExpenses(
            @PathVariable Long id,
            @RequestParam int year,
            @RequestParam int month) {
        BigDecimal expenses = cardService.calculateMonthlyExpenses(id, year, month);
        return ResponseEntity.ok(expenses);
    }
}
