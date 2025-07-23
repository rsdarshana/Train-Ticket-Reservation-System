package com.trainsystem.repository;

import com.trainsystem.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    Optional<Train> findByTrainNumber(String trainNumber);
    
    @Query("SELECT t FROM Train t LEFT JOIN FETCH t.schedules")
    List<Train> findAllWithSchedules();
    
    @Query("SELECT t FROM Train t LEFT JOIN FETCH t.schedules WHERE t.trainNumber = :trainNumber")
    Optional<Train> findByTrainNumberWithSchedules(@Param("trainNumber") String trainNumber);
}