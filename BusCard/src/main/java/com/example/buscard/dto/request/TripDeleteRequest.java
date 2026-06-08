package com.example.buscard.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDeleteRequest {

    @NotNull(message = "Trip ID is required")
    private Long id;
}
