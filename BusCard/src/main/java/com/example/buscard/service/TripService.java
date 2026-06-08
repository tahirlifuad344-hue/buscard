package com.example.buscard.service;

import com.example.buscard.dto.request.TripCreateRequest;
import com.example.buscard.dto.request.TripUpdateRequest;
import com.example.buscard.entity.Card;
import com.example.buscard.entity.Trip;
import com.example.buscard.exception.TapilmadiException;
import com.example.buscard.mapper.TripMapper;
import com.example.buscard.repository.CardRepository;
import com.example.buscard.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final CardRepository cardRepository;
    private final TripMapper tripMapper;
    private final CardService cardService;

    // Yeni səfər yaratmaq və kartdan ödəniş etmək
    @Transactional
    public Trip createTrip(TripCreateRequest request) {
        // Kartı tap
        Card card = cardRepository.findById(request.getCardId())
                .orElseThrow(() -> new TapilmadiException("Kart tapılmadı: " + request.getCardId()));

        // Kartdan ödəniş et
        cardService.makePayment(request.getCardId(), request.getFare());

        // Səfəri yarat
        Trip trip = tripMapper.toEntity(request, card);
        return tripRepository.save(trip);
    }

    // Səfəri yeniləmək
    @Transactional
    public Trip updateTrip(Long tripId, TripUpdateRequest request) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TapilmadiException("Səfər tapılmadı: " + tripId));

        tripMapper.updateEntity(trip, request);
        return tripRepository.save(trip);
    }

    // Səfəri silmək
    @Transactional
    public void deleteTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TapilmadiException("Səfər tapılmadı: " + tripId));

        tripRepository.delete(trip);
    }

    // Səfəri ID-yə görə tapmaq
    @Transactional(readOnly = true)
    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new TapilmadiException("Səfər tapılmadı: " + tripId));
    }

    // Marşrut adına görə səfər tapmaq
    @Transactional(readOnly = true)
    public Trip getTripByRouteName(String routeName) {
        Trip trip = tripRepository.findByRouteName(routeName);
        if (trip == null) {
            throw new TapilmadiException("Səfər tapılmadı: " + routeName);
        }
        return trip;
    }

    // Bütün səfərləri əldə etmək
    @Transactional(readOnly = true)
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
}
