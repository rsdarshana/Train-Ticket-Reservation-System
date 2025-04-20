package com.trainsystem.repository;

import com.trainsystem.model.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Long> {
    
    // Updated query without trainType and seatClass parameters
    @Query("SELECT ts FROM TrainSchedule ts WHERE " +
           "(:source IS NULL OR ts.sourceStation = :source) AND " +
           "(:destination IS NULL OR ts.destinationStation = :destination) AND " +
           "(:minDeparture IS NULL OR ts.departureTime >= :minDeparture) AND " +
           "(:maxDeparture IS NULL OR ts.departureTime <= :maxDeparture) " +
           "ORDER BY " +
           "CASE WHEN :sortBy = 'fastest' THEN ts.durationMinutes END ASC, " +
           "CASE WHEN :sortBy = 'least_stops' THEN SIZE(ts.stoppages) END ASC, " +
           "CASE WHEN :sortBy = 'lowest_fare' THEN ts.fare END ASC")
    List<TrainSchedule> findFilteredSchedules(
            @Param("source") String source,
            @Param("destination") String destination,
            @Param("minDeparture") LocalDateTime minDeparture,
            @Param("maxDeparture") LocalDateTime maxDeparture,
            @Param("sortBy") String sortBy);
    
    // Query for displaying all schedules with train information
    @Query("SELECT ts FROM TrainSchedule ts LEFT JOIN FETCH ts.train ORDER BY ts.departureTime")
    List<TrainSchedule> findAllSchedules();
    
    // Updated query without trainType and seatClass parameters
    @Query("SELECT ts FROM TrainSchedule ts LEFT JOIN FETCH ts.train WHERE " +
           "(:source IS NULL OR :source = '' OR ts.sourceStation = :source) AND " +
           "(:destination IS NULL OR :destination = '' OR ts.destinationStation = :destination) " +
           "ORDER BY " +
           "CASE WHEN :sortBy = 'fastest' THEN ts.durationMinutes END ASC, " +
           "CASE WHEN :sortBy = 'least_stops' THEN SIZE(ts.stoppages) END ASC, " +
           "CASE WHEN :sortBy = 'lowest_fare' THEN ts.fare END ASC, " +
           "ts.departureTime ASC")
    List<TrainSchedule> findFilteredSchedulesBySourceAndDestination(
            @Param("source") String source,
            @Param("destination") String destination,
            @Param("sortBy") String sortBy);
    
    // Query for fetching by train number with eager loading
    @Query("SELECT ts FROM TrainSchedule ts LEFT JOIN FETCH ts.train WHERE ts.train.trainNumber = :trainNumber")
    List<TrainSchedule> findByTrainTrainNumber(@Param("trainNumber") String trainNumber);
    
    // Query to find schedules for a specific route
    @Query("SELECT ts FROM TrainSchedule ts LEFT JOIN FETCH ts.train WHERE " +
           "ts.sourceStation = :source AND ts.destinationStation = :destination AND " +
           "ts.departureTime >= :departureDate")
    List<TrainSchedule> findSchedulesForRoute(
            @Param("source") String source,
            @Param("destination") String destination,
            @Param("departureDate") LocalDateTime departureDate);
    
    // Query to check if seats are available for a schedule
    @Query("SELECT ts FROM TrainSchedule ts WHERE ts.id = :scheduleId AND ts.availableSeats >= :requiredSeats")
    Optional<TrainSchedule> findScheduleWithAvailableSeats(
            @Param("scheduleId") Long scheduleId,
            @Param("requiredSeats") int requiredSeats);
}