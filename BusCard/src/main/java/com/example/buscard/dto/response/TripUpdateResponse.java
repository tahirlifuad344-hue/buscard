package com.example.buscard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripUpdateResponse {

    private Long id;
    private String routeName;

    private BigDecimal fare;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
