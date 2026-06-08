package com.example.buscard.controller;

import com.example.buscard.dto.request.TripCreateRequest;
import com.example.buscard.dto.request.TripUpdateRequest;
import com.example.buscard.dto.response.TripCreateResponse;
import com.example.buscard.dto.response.TripDeleteResponse;
import com.example.buscard.dto.response.TripReadResponse;
import com.example.buscard.dto.response.TripUpdateResponse;
import com.example.buscard.entity.Trip;
import com.example.buscard.mapper.TripMapper;
import com.example.buscard.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    private final TripMapper tripMapper;

    // Yeni səfər yaratmaq
    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@Valid @RequestBody TripCreateRequest request) {
        Trip trip = tripService.createTrip(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tripMapper.toCreateResponse(trip));
    }

    // Səfəri ID-yə görə oxumaq
    @GetMapping("/{id}")
    public ResponseEntity<TripReadResponse> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(tripMapper.toReadResponse(trip));
    }

    // Bütün səfərləri oxumaq
    @GetMapping
    public ResponseEntity<List<TripReadResponse>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        List<TripReadResponse> responses = trips.stream()
                .map(tripMapper::toReadResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // Marşrut adına görə səfər tapmaq
    @GetMapping("/route/{routeName}")
    public ResponseEntity<TripReadResponse> getTripByRouteName(@PathVariable String routeName) {
        Trip trip = tripService.getTripByRouteName(routeName);
        return ResponseEntity.ok(tripMapper.toReadResponse(trip));
    }

    // Səfəri yeniləmək
    @PutMapping("/{id}")
    public ResponseEntity<TripUpdateResponse> updateTrip(
            @PathVariable Long id,
            @Valid @RequestBody TripUpdateRequest request) {
        Trip trip = tripService.updateTrip(id, request);
        return ResponseEntity.ok(tripMapper.toUpdateResponse(trip));
    }

    // Səfəri silmək
    @DeleteMapping("/{id}")
    public ResponseEntity<TripDeleteResponse> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.ok(tripMapper.toDeleteResponse(id, "Səfər uğurla silindi"));
    }
}
