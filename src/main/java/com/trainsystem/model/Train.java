package com.trainsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TRAIN")
@Data
@NoArgsConstructor
public class Train {
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
    private List<TrainSchedule> schedules = new ArrayList<>();
    
    public Train(String trainNumber, String name, int totalSeats) {
        this.trainNumber = trainNumber;
        this.name = name;
        this.totalSeats = totalSeats;
    }
    
    public void addSchedule(TrainSchedule schedule) {
        schedules.add(schedule);
        schedule.setTrain(this);
    }
    
    public void removeSchedule(TrainSchedule schedule) {
        schedules.remove(schedule);
        schedule.setTrain(null);
    }

    public Long getId() {
        return id;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getName() {
        return name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public List<TrainSchedule> getSchedules() {
        return schedules;
    }
}