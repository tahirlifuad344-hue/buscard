package com.example.buscard.mapper;

import com.example.buscard.dto.request.CardCreateRequest;
import com.example.buscard.dto.request.CardUpdateRequest;
import com.example.buscard.dto.response.CardCreateResponse;
import com.example.buscard.dto.response.CardDeleteResponse;
import com.example.buscard.dto.response.CardReadResponse;
import com.example.buscard.dto.response.CardUpdateResponse;
import com.example.buscard.entity.Card;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CardMapper {

    public Card toEntity(CardCreateRequest request) {
        Card card = new Card();
        card.setCardNumber(Long.parseLong(String.valueOf(request.getCardNumber())));
        card.setBalance(request.getBalance());
        card.setCreatedAt(LocalDateTime.now());
        return card;
    }

    public void updateEntity(Card card, CardUpdateRequest request) {
        if (request.getBalance() != null) {
            card.setBalance(request.getBalance());
        }
    }

    public CardCreateResponse toCreateResponse(Card card) {
        return CardCreateResponse.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .balance(card.getBalance())
                .createdAt(card.getCreatedAt())
                .build();
    }

    public CardReadResponse toReadResponse(Card card) {
        return CardReadResponse.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .balance(card.getBalance())
                .createdAt(card.getCreatedAt())
                .build();
    }

    public CardUpdateResponse toUpdateResponse(Card card) {
        return CardUpdateResponse.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .balance(card.getBalance())
                .build();
    }

    public CardDeleteResponse toDeleteResponse(Long id, String mesaj) {
        return CardDeleteResponse.builder()
                .id(id)
                .message(mesaj)
                .build();
    }
}
