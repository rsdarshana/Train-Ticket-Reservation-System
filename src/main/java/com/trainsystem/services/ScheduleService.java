package com.trainsystem.services;

import com.trainsystem.model.ScheduleFilter;
import com.trainsystem.model.Train;
import com.trainsystem.model.TrainSchedule;
import com.trainsystem.model.UserPreference;
import com.trainsystem.repository.TrainRepository;
import com.trainsystem.repository.TrainScheduleRepository;
import com.trainsystem.repository.UserPreferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ScheduleService {
    
    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final TrainScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    
    public ScheduleService(TrainScheduleRepository scheduleRepository,
                         TrainRepository trainRepository,
                         UserPreferenceRepository userPreferenceRepository) {
        this.scheduleRepository = scheduleRepository;
        this.trainRepository = trainRepository;
        this.userPreferenceRepository = userPreferenceRepository;
    }
    
    @Transactional(readOnly = true)
    public List<TrainSchedule> getAllSchedules() {
        try {
            List<TrainSchedule> schedules = scheduleRepository.findAllSchedules();
            logger.info("Retrieved {} schedules", schedules.size());
            return schedules;
        } catch (Exception e) {
            logger.error("Error retrieving all schedules", e);
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public List<TrainSchedule> getFilteredSchedules(ScheduleFilter filter, String username) {
        try {
            if (username != null) {
                UserPreference preference = userPreferenceRepository.findByUserUsername(username)
                        .orElse(null);
                
                if (preference != null && preference.getSavedFilter() != null) {
                    ScheduleFilter savedFilter = preference.getSavedFilter();
                    if (filter.getSortBy() == null) filter.setSortBy(savedFilter.getSortBy());
                }
            }
            
            List<TrainSchedule> schedules = scheduleRepository.findFilteredSchedulesBySourceAndDestination(
                    filter.getSourceStation(),
                    filter.getDestinationStation(),
                    filter.getSortBy()
            );
            
            logger.info("Retrieved {} filtered schedules", schedules.size());
            return schedules;
        } catch (Exception e) {
            logger.error("Error retrieving filtered schedules", e);
            throw e;
        }
    }
    
    @Transactional
    public void saveFilterPreferences(String username, ScheduleFilter filter) {
        userPreferenceRepository.findByUserUsername(username).ifPresent(preference -> {
            preference.setSavedFilter(filter);
            userPreferenceRepository.save(preference);
        });
    }
    
    @Transactional(readOnly = true)
    public List<TrainSchedule> getSchedulesByTrainNumber(String trainNumber) {
        try {
            List<TrainSchedule> schedules = scheduleRepository.findByTrainTrainNumber(trainNumber);
            logger.info("Retrieved {} schedules for train number {}", schedules.size(), trainNumber);
            return schedules;
        } catch (Exception e) {
            logger.error("Error retrieving schedules by train number", e);
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public Train getTrainWithSchedules(String trainNumber) {
        try {
            Optional<Train> trainOptional = trainRepository.findByTrainNumberWithSchedules(trainNumber);
            if (trainOptional.isPresent()) {
                Train train = trainOptional.get();
                logger.info("Retrieved train {} with {} schedules", 
                    train.getTrainNumber(), train.getSchedules().size());
                return train;
            } else {
                logger.warn("Train number {} not found", trainNumber);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error retrieving train with schedules", e);
            throw e;
        }
    }
    
    @Transactional
    public TrainSchedule updateScheduleAvailability(Long scheduleId, int bookedSeats) {
        TrainSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
                
        if (schedule.getAvailableSeats() < bookedSeats) {
            throw new RuntimeException("Not enough available seats");
        }
        
        schedule.setAvailableSeats(schedule.getAvailableSeats() - bookedSeats);
        return scheduleRepository.save(schedule);
    }
    
    @Transactional(readOnly = true)
    public List<TrainSchedule> findSchedulesForRoute(String source, String destination, LocalDate departureDate) {
        LocalDateTime departureDateTime = departureDate.atStartOfDay();
        return scheduleRepository.findSchedulesForRoute(source, destination, departureDateTime);
    }
}
