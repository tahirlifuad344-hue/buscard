package com.example.buscard.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CardCreateRequest {
    @NotNull
    private Long cardNumber;
    @NotNull
    private Long balance;


}
