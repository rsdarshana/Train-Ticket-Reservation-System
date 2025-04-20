package com.trainsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "BOOKED_SEATS")
@Data
public class BookedSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    @Column(name = "username", nullable = false)
    private String username;

    //@ManyToOne
    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    //@ManyToOne
    @Column(name = "train_schedule_id", nullable = false)
    private Long trainScheduleId;

    @Column(name = "booking_time")
    private LocalDateTime bookingTime = LocalDateTime.now();

    @Column(name = "fare")
    private double fare;

    @Column(name = "status")
    private String status = "CONFIRMED";  // or CANCELLED

    public BookedSeat() {
    }

    public BookedSeat(Long id, String username, Long seatId, Long trainScheduleId, LocalDateTime bookingTime, double fare, String status) {
        this.id = id;
        this.username = username;
        this.seatId = seatId;
        this.trainScheduleId = trainScheduleId;
        this.bookingTime = bookingTime;
        this.fare = fare;
        this.status = status;
    }

    public BookedSeat(String user, Long seat, Long trainSchedule, double fare) {
        this.username = user;
        this.seatId = seat;
        this.trainScheduleId = trainSchedule;
        this.fare = fare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getTrainScheduleId() {
        return trainScheduleId;
    }

    public void setTrainScheduleId(Long trainScheduleId) {
        this.trainScheduleId = trainScheduleId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
