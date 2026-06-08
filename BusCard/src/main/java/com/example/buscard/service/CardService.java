package com.example.buscard.service;

import com.example.buscard.dto.request.CardCreateRequest;
import com.example.buscard.entity.Card;
import com.example.buscard.entity.Trip;
import com.example.buscard.exception.ArtiqMovcuddurException;
import com.example.buscard.exception.TapilmadiException;
import com.example.buscard.exception.YersizIstekException;
import com.example.buscard.repository.CardRepository;
import com.example.buscard.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final TripRepository tripRepository;

    // Yeni kart yaratmaq
    @Transactional
    public Card createCard(CardCreateRequest request) {
        // Kart nömrəsi artıq mövcuddursa xəta
        if (cardRepository.findByCardNumber(Long.parseLong(String.valueOf(request.getCardNumber()))) != null) {
            throw new ArtiqMovcuddurException("Bu kart nömrəsi artıq mövcuddur: " + request.getCardNumber());
        }

        Card card = new Card();
        card.setCardNumber(Long.parseLong(String.valueOf(request.getCardNumber())));
        card.setBalance(request.getBalance());
        card.setCreatedAt(LocalDateTime.now());

        return cardRepository.save(card);
    }

    // Balans artırmaq
    @Transactional
    public Card increaseBalance(Long cardId, Long amount) {
        if (amount == null || amount <= 0) {
            throw new YersizIstekException("Məbləğ müsbət olmalıdır");
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new TapilmadiException("Kart tapılmadı: " + cardId));

        card.setBalance(card.getBalance() + amount);
        return cardRepository.save(card);
    }

    // Kartdan ödəniş etmək
    @Transactional
    public Card makePayment(Long cardId, BigDecimal fare) {
        if (fare == null || fare.compareTo(BigDecimal.ZERO) <= 0) {
            throw new YersizIstekException("Ödəniş məbləği müsbət olmalıdır");
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new TapilmadiException("Kart tapılmadı: " + cardId));

        long fareInLong = fare.longValue();
        
        if (card.getBalance() < fareInLong) {
            throw new YersizIstekException("Kartda kifayət qədər balans yoxdur. Balans: " + card.getBalance() + ", Ödəniş: " + fareInLong);
        }

        card.setBalance(card.getBalance() - fareInLong);
        return cardRepository.save(card);
    }

    // Səfər tarixçəsini göstərmək
    @Transactional(readOnly = true)
    public List<Trip> getTripHistory(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new TapilmadiException("Kart tapılmadı: " + cardId));

        return card.getTrips();
    }

    // Aylıq xərcləri hesablamaq
    @Transactional(readOnly = true)
    public BigDecimal calculateMonthlyExpenses(Long cardId, int year, int month) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new TapilmadiException("Kart tapılmadı: " + cardId));

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        return card.getTrips().stream()
                .filter(trip -> trip.getTripDate() != null)
                .filter(trip -> !trip.getTripDate().isBefore(startDate) && !trip.getTripDate().isAfter(endDate))
                .map(Trip::getFare)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Kart məlumatlarını əldə etmək
    @Transactional(readOnly = true)
    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new TapilmadiException("Kart tapılmadı: " + cardId));
    }

    // Kart nömrəsinə görə kart tapmaq
    @Transactional(readOnly = true)
    public Card getCardByCardNumber(Long cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            throw new TapilmadiException("Kart tapılmadı: " + cardNumber);
        }
        return card;
    }

    // Bütün kartları əldə etmək
    @Transactional(readOnly = true)
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    // Kartı yeniləmək
    @Transactional
    public Card updateCard(Long id, Long balance) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new TapilmadiException("Kart tapılmadı: " + id));

        if (balance != null) {
            if (balance < 0) {
                throw new YersizIstekException("Balans mənfi ola bilməz");
            }
            card.setBalance(balance);
        }

        return cardRepository.save(card);
    }

    // Kartı silmək
    @Transactional
    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new TapilmadiException("Kart tapılmadı: " + id));

        cardRepository.delete(card);
    }
}
