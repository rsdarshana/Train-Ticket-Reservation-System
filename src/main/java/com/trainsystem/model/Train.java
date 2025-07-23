package com.trainsystem.model;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TRAIN")
@ToString
public class Train {

    // Singleton instance
    private static Train instance;

    // Private constructor to prevent instantiation
    private Train() {
        this.schedules = new ArrayList<>();
    }

    // Synchronized method to ensure thread safety
    public static synchronized Train getInstance() {
        if (instance == null) {
            instance = new Train();
        }
        return instance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRAIN_NUMBER", nullable = false, unique = true)
    private String trainNumber;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "TOTAL_SEATS", nullable = false)
    private int totalSeats;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<TrainSchedule> schedules;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public List<TrainSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<TrainSchedule> schedules) {
        this.schedules = schedules;
    }

    // Helper methods
    public void addSchedule(TrainSchedule schedule) {
        schedules.add(schedule);
        schedule.setTrain(this);
    }

    public void removeSchedule(TrainSchedule schedule) {
        schedules.remove(schedule);
        schedule.setTrain(null);
    }
}
