package com.example.buscard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routeName;
    private BigDecimal fare;
    private LocalDateTime tripDate;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}