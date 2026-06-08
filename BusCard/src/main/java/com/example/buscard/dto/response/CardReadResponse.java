package com.example.buscard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
@NonNull
@AllArgsConstructor
public class CardReadResponse {
    private Long id;
    private Long cardNumber;
    private Long balance;
    private LocalDateTime createdAt;
}
