package com.example.buscard.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CardDeleteRequest {
    @NotNull

    private Long id;
}
