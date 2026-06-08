package com.example.buscard.dto.request;

import com.example.buscard.entity.Trip;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class CardUpdateRequest {
    @NotNull
    private Long balance;
    @NotNull
    private Long id;


}
