package com.trainsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TRAIN_SCHEDULE")
@Data
@NoArgsConstructor
public class TrainSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TRAIN_ID", nullable = false)
    private Train train;
    
    @Column(name = "SOURCE_STATION", nullable = false)
    private String sourceStation;
    
    @Column(name = "DESTINATION_STATION", nullable = false)
    private String destinationStation;
    
    @Column(name = "DEPARTURE_TIME", nullable = false)
    private LocalDateTime departureTime;
    
    @Column(name = "ARRIVAL_TIME", nullable = false)
    private LocalDateTime arrivalTime;
    
    @Column(name = "DURATION_MINUTES", nullable = false)
    private int durationMinutes;
    
    @Column(name = "AVAILABLE_SEATS", nullable = false)
    private int availableSeats;
    
    @Column(name = "FARE", nullable = false)
    private double fare;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "TRAIN_SCHEDULE_STOPPAGES",
        joinColumns = @JoinColumn(name = "TRAIN_SCHEDULE_ID")
    )
    @Column(name = "STOPPAGES")
    @OrderColumn(name = "STOP_ORDER")
    private List<String> stoppages = new ArrayList<>();
    
    @OneToMany(mappedBy = "trainSchedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Seat> seats = new ArrayList<>();
    
    public TrainSchedule(Train train, String sourceStation, String destinationStation, 
                        LocalDateTime departureTime, LocalDateTime arrivalTime,
                        int durationMinutes, int availableSeats, 
                        double fare, List<String> stoppages) {
        this.train = train;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.durationMinutes = durationMinutes;
        this.availableSeats = availableSeats;
        this.fare = fare;
        this.stoppages = stoppages;
    }
    
    public void addSeat(Seat seat) {
        seats.add(seat);
        seat.setTrainSchedule(this);
    }

    public Long getId() {
        return id;
    }

    public Train getTrain() {
        return train;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getFare() {
        return fare;
    }

    public List<String> getStoppages() {
        return stoppages;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}