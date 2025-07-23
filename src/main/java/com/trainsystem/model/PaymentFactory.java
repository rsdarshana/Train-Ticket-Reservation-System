package com.trainsystem.model;

public class PaymentFactory {

    public static Payment createSuccessPayment(String cardHolderName, String last4Digits, BookedSeat booking) {
        Payment payment = new Payment();
        payment.setCardHolderName(cardHolderName);
        payment.setMaskedCardNumber("**** **** **** " + last4Digits);
        payment.setPaymentStatus("SUCCESS");
        payment.setBooking(booking);
        return payment;
    }

    public static Payment createFailedPayment(String cardHolderName, String last4Digits, BookedSeat booking) {
        Payment payment = new Payment();
        payment.setCardHolderName(cardHolderName);
        payment.setMaskedCardNumber("**** **** **** " + last4Digits);
        payment.setPaymentStatus("FAILED");
        payment.setBooking(booking);
        return payment;
    }
}
