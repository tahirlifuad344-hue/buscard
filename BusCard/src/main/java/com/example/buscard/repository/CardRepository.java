package com.example.buscard.repository;

import com.example.buscard.entity.Card;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByCardNumber(@NonNull @Min(5) @Max(5) Long cardNumber);
    List<Card> findAll();
}
