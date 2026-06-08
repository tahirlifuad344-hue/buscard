package com.example.buscard.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripCreateRequest {

    @NotBlank(message = "Route name is required")
    private String routeName;

    @NotNull(message = "fare is required")
    private BigDecimal fare;
    @NotNull(message = "card id is required")
    private Long cardId;
    @NotNull(message = "trip date is required")
    private LocalDateTime tripDate;


}
