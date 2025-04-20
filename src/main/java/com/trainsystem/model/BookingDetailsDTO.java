package com.trainsystem.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDetailsDTO {
    // From BookedSeat
    private Long bookingId;
    private LocalDateTime bookingTime;
    //private double fare;
    private String bookingStatus;

    // From Seat
    private String coachNumber;
    private String seatNumber;
    private Seat.CoachType coachType;
    private Seat.SeatType seatType;
    private Long seatId;

    // From TrainSchedule
    private Long scheduleId;
    private String trainName;
    private String trainNumber;
    private String sourceStation;
    private String destinationStation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int durationMinutes;

    public BookingDetailsDTO() {
    }

    public BookingDetailsDTO(Long bookingId, LocalDateTime bookingTime /*double fare*/, String bookingStatus,
                             String coachNumber, String seatNumber,
                             Seat.CoachType coachType, Seat.SeatType seatType,
                             Long scheduleId, String trainName, String trainNumber,
                             String sourceStation, String destinationStation,
                             LocalDateTime departureTime, LocalDateTime arrivalTime,
                             int durationMinutes, Long seatId) {
        this.bookingId = bookingId;
        this.bookingTime = bookingTime;
        //this.fare = fare;
        this.bookingStatus = bookingStatus;
        this.coachNumber = coachNumber;
        this.seatNumber = seatNumber;
        this.coachType = coachType;
        this.seatType = seatType;
        this.scheduleId = scheduleId;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.durationMinutes = durationMinutes;
        this.seatId = seatId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getCoachNumber() {
        return coachNumber;
    }

    public void setCoachNumber(String coachNumber) {
        this.coachNumber = coachNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Seat.CoachType getCoachType() {
        return coachType;
    }

    public void setCoachType(Seat.CoachType coachType) {
        this.coachType = coachType;
    }

    public Seat.SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(Seat.SeatType seatType) {
        this.seatType = seatType;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}