package com.example.buscard.mapper;

import com.example.buscard.dto.request.TripCreateRequest;
import com.example.buscard.dto.request.TripUpdateRequest;
import com.example.buscard.dto.response.TripCreateResponse;
import com.example.buscard.dto.response.TripDeleteResponse;
import com.example.buscard.dto.response.TripReadResponse;
import com.example.buscard.dto.response.TripUpdateResponse;
import com.example.buscard.entity.Card;
import com.example.buscard.entity.Trip;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TripMapper {

    public Trip toEntity(TripCreateRequest request, Card card) {
        Trip trip = new Trip();
        trip.setRouteName(request.getRouteName());
        trip.setFare(request.getFare());
        trip.setTripDate(request.getTripDate());
        trip.setCard(card);
        trip.setCreatedAt(LocalDateTime.now());
        return trip;
    }

    public void updateEntity(Trip trip, TripUpdateRequest request) {
        if (request.getRouteName() != null) {
            trip.setRouteName(request.getRouteName());
        }
        if (request.getFare() != null) {
            trip.setFare(request.getFare());
        }
        if (request.getTripDate() != null) {
            trip.setTripDate(request.getTripDate());
        }
    }

    public TripCreateResponse toCreateResponse(Trip trip) {
        return TripCreateResponse.builder()
                .id(trip.getId())
                .routeName(trip.getRouteName())
                .fare(trip.getFare())
                .createdAt(trip.getCreatedAt())
                .build();
    }

    public TripReadResponse toReadResponse(Trip trip) {
        return TripReadResponse.builder()
                .id(trip.getId())
                .routeName(trip.getRouteName())
                .departureTime(trip.getTripDate())
                .fare(trip.getFare())
                .createdAt(trip.getCreatedAt())
                .build();
    }

    public TripUpdateResponse toUpdateResponse(Trip trip) {
        return TripUpdateResponse.builder()
                .id(trip.getId())
                .routeName(trip.getRouteName())
                .fare(trip.getFare())
                .createdAt(trip.getCreatedAt())
                .build();
    }

    public TripDeleteResponse toDeleteResponse(Long id, String mesaj) {
        return TripDeleteResponse.builder()
                .id(id)
                .message(mesaj)
                .build();
    }
}
