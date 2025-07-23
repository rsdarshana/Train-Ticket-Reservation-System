// src/main/java/com/trainsystem/repository/SeatRepository.java
package com.trainsystem.repository;

import com.trainsystem.model.Seat;
import com.trainsystem.model.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByTrainScheduleAndCoachTypeAndSeatTypeAndIsBookedFalse(
        TrainSchedule trainSchedule, Seat.CoachType coachType, Seat.SeatType seatType);
    
    List<Seat> findByTrainSchedule(TrainSchedule trainSchedule);
}