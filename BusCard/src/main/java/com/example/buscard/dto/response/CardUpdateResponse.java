package com.example.buscard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardUpdateResponse {
    private Long id;
    private Long cardNumber;
    private Long balance;
    private LocalDateTime createdAt;
}
