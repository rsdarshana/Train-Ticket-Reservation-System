package com.trainsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.time.LocalTime;

@Data
@Embeddable
public class ScheduleFilter {
    
    
    @Column(name = "SAVED_FILTER_SOURCE")
    private String sourceStation;
    
    @Column(name = "SAVED_FILTER_DESTINATION")
    private String destinationStation;
    
    @Column(name = "SAVED_FILTER_MIN_DEPARTURE")
    private LocalTime minDepartureTime;
    
    @Column(name = "SAVED_FILTER_MAX_DEPARTURE")
    private LocalTime maxDepartureTime;
    
    @Column(name = "SAVED_FILTER_MAX_DURATION")
    private Integer maxDuration;
    
    
    
    @Column(name = "SAVED_FILTER_SORT_BY")
    private String sortBy; // fastest, least_stops, lowest_fare
}