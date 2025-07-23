// src/main/java/com/trainsystem/repository/port/SeatRepositoryPort.java
package com.trainsystem.repository.port;

import com.trainsystem.model.Seat;
import com.trainsystem.model.TrainSchedule;
import java.util.List;

public interface SeatRepositoryPort {
    List<Seat> findAvailableSeats(TrainSchedule trainSchedule, Seat.CoachType coachType, Seat.SeatType seatType);
    List<Seat> findSeatsBySchedule(TrainSchedule trainSchedule);
}
