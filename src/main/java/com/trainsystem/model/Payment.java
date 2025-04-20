package com.trainsystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardHolderName;
    private String maskedCardNumber; // Store last 4 digits like **** **** **** 1234
    private String paymentStatus; // SUCCESS, FAILED

    @Column(name = "payment_time")
    private LocalDateTime paymentTime = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "booking_id")
    private BookedSeat booking;

    // Getters and Setters
}
