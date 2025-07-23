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

    // Constructor is package-private to restrict creation to factory within same package
    Payment() {}

    // Public getters and setters
    public Long getId() { return id; }

    public String getCardHolderName() { return cardHolderName; }
    public void setCardHolderName(String cardHolderName) { this.cardHolderName = cardHolderName; }

    public String getMaskedCardNumber() { return maskedCardNumber; }
    public void setMaskedCardNumber(String maskedCardNumber) { this.maskedCardNumber = maskedCardNumber; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public LocalDateTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }

    public BookedSeat getBooking() { return booking; }
    public void setBooking(BookedSeat booking) { this.booking = booking; }
}
