package com.example.buscard.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@NotNull
@AllArgsConstructor
public class CardCreateResponse {
private Long id;
private Long cardNumber;
private Long balance;
private LocalDateTime createdAt;
}
