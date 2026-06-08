package com.example.buscard.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripUpdateRequest {

    private String routeName;
   private Long cardId;
   private LocalDateTime TripDate;

    private BigDecimal fare;

}
