package com.trainsystem.facade;

import com.trainsystem.model.ScheduleFilter;
import com.trainsystem.model.Train;
import com.trainsystem.model.TrainSchedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleFacade {

    List<TrainSchedule> getAllSchedules();

    List<TrainSchedule> getFilteredSchedules(ScheduleFilter filter, String username);

    void saveFilterPreferences(String username, ScheduleFilter filter);

    List<TrainSchedule> getSchedulesByTrainNumber(String trainNumber);

    Train getTrainWithSchedules(String trainNumber);

    TrainSchedule updateScheduleAvailability(Long scheduleId, int bookedSeats);

    List<TrainSchedule> findSchedulesForRoute(String source, String destination, LocalDate departureDate);
}
