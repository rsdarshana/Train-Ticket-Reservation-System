// src/main/java/com/trainsystem/repository/adapter/SeatRepositoryAdapter.java
package com.trainsystem.repository.adapter;

import com.trainsystem.model.Seat;
import com.trainsystem.model.TrainSchedule;
import com.trainsystem.repository.SeatRepository;
import com.trainsystem.repository.port.SeatRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SeatRepositoryAdapter implements SeatRepositoryPort {

    private final SeatRepository seatRepository;

    public SeatRepositoryAdapter(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public List<Seat> findAvailableSeats(TrainSchedule trainSchedule, Seat.CoachType coachType, Seat.SeatType seatType) {
        return seatRepository.findByTrainScheduleAndCoachTypeAndSeatTypeAndIsBookedFalse(trainSchedule, coachType, seatType);
    }

    @Override
    public List<Seat> findSeatsBySchedule(TrainSchedule trainSchedule) {
        return seatRepository.findByTrainSchedule(trainSchedule);
    }
}
