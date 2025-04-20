// src/main/java/com/trainsystem/model/Seat.java
package com.trainsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SEAT")
@Data
@NoArgsConstructor
@Setter
@Getter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAIN_SCHEDULE_ID", nullable = false)
    private TrainSchedule trainSchedule;
    
    @Column(name = "COACH_NUMBER", nullable = false)
    private String coachNumber;
    
    @Column(name = "SEAT_NUMBER", nullable = false)
    private String seatNumber;
    
    @Column(name = "COACH_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CoachType coachType;
    
    @Column(name = "SEAT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Column(name = "FARE", nullable = false)
    private double fare;
    
    @Column(name = "IS_BOOKED", nullable = false)
    private boolean isBooked = false;

    public void setBooked(boolean val) {
        this.isBooked = val;
    }

    public enum CoachType {
        SLEEPER_AC, SLEEPER_NON_AC, CHAIR_CAR_AC, CHAIR_CAR_NON_AC
    }
    
    public enum SeatType {
        WINDOW, MIDDLE, AISLE
    }
    
    public Seat(TrainSchedule trainSchedule, String coachNumber, String seatNumber, 
                CoachType coachType, SeatType seatType) {
        this.trainSchedule = trainSchedule;
        this.coachNumber = coachNumber;
        this.seatNumber = seatNumber;
        this.coachType = coachType;
        this.seatType = seatType;
    }

    public TrainSchedule getTrainSchedule() {
        return trainSchedule;
    }

    public String getCoachNumber() {
        return coachNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public CoachType getCoachType() {
        return coachType;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTrainSchedule(TrainSchedule trainSchedule) {
        this.trainSchedule = trainSchedule;
    }

    public void setCoachNumber(String coachNumber) {
        this.coachNumber = coachNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setCoachType(CoachType coachType) {
        this.coachType = coachType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }
}