package com.trainsystem.controller;

import com.trainsystem.facade.ScheduleFacade; // ✅ Updated import
import com.trainsystem.model.ScheduleFilter;
import com.trainsystem.model.TrainSchedule;
import com.trainsystem.services.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    private final ScheduleFacade scheduleFacade; // ✅ Renamed and updated type
    private final UserService userService;

    public ScheduleController(ScheduleFacade scheduleFacade, UserService userService) {
        this.scheduleFacade = scheduleFacade;
        this.userService = userService;
    }

    @GetMapping("/view")
    public String viewSchedules(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        try {
            logger.info("Fetching all schedules");
            List<TrainSchedule> schedules = scheduleFacade.getAllSchedules(); // ✅ Updated call
            logger.info("Retrieved {} schedules", schedules.size());

            int nullTrainCount = 0;
            for (TrainSchedule schedule : schedules) {
                if (schedule.getTrain() == null) {
                    nullTrainCount++;
                }
            }
            if (nullTrainCount > 0) {
                logger.warn("{} schedules have null train objects", nullTrainCount);
            }

            model.addAttribute("schedules", schedules);
            model.addAttribute("filtered", false);

            if (userDetails != null) {
                model.addAttribute("username", userDetails.getUsername());
            }

            return "schedule/view";
        } catch (Exception e) {
            logger.error("Error loading schedules: ", e);
            model.addAttribute("schedules", Collections.emptyList());
            model.addAttribute("errorMessage", "Failed to load schedules: " + e.getMessage());

            if (userDetails != null) {
                model.addAttribute("username", userDetails.getUsername());
            }

            return "schedule/view";
        }
    }

    @GetMapping("/filter")
    public String showFilterForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("filter", new ScheduleFilter());

        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }

        return "schedule/filter";
    }

    @PostMapping("/filter")
    public String applyFilter(@AuthenticationPrincipal UserDetails userDetails,
                              @ModelAttribute ScheduleFilter filter,
                              Model model) {
        try {
            logger.info("Filtering schedules - source: {}, destination: {}",
                    filter.getSourceStation(), filter.getDestinationStation());

            List<TrainSchedule> filteredSchedules = scheduleFacade.getFilteredSchedules( // ✅ Updated
                    filter, userDetails != null ? userDetails.getUsername() : null);

            logger.info("Retrieved {} filtered schedules", filteredSchedules.size());

            model.addAttribute("schedules", filteredSchedules);
            model.addAttribute("filtered", true);

            if (userDetails != null) {
                model.addAttribute("username", userDetails.getUsername());
            }

            return "schedule/view";
        } catch (Exception e) {
            logger.error("Error filtering schedules: ", e);
            model.addAttribute("schedules", Collections.emptyList());
            model.addAttribute("errorMessage", "Failed to filter schedules: " + e.getMessage());

            if (userDetails != null) {
                model.addAttribute("username", userDetails.getUsername());
            }

            return "schedule/view";
        }
    }

    @PostMapping("/savePreferences")
    public String saveFilterPreferences(@AuthenticationPrincipal UserDetails userDetails,
                                        @ModelAttribute ScheduleFilter filter) {
        if (userDetails != null) {
            try {
                scheduleFacade.saveFilterPreferences(userDetails.getUsername(), filter); // ✅ Updated
                return "redirect:/schedule/filter?preferencesSaved";
            } catch (Exception e) {
                logger.error("Error saving preferences: ", e);
                return "redirect:/schedule/filter?error";
            }
        }

        return "redirect:/schedule/filter?error";
    }

    @GetMapping("/train/{trainNumber}")
    public String viewTrainSchedule(@PathVariable String trainNumber,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    Model model) {
        try {
            logger.info("Fetching schedules for train number: {}", trainNumber);
            List<TrainSchedule> schedules = scheduleFacade.getSchedulesByTrainNumber(trainNumber); // ✅ Updated
            logger.info("Retrieved {} schedules for train {}", schedules.size(), trainNumber);

            model.addAttribute("schedules", schedules);
            model.addAttribute("filtered", true);

            if (userDetails != null) {
                model.addAttribute("username", userDetails.getUsername());
            }

            return "schedule/view";
        } catch (Exception e) {
            logger.error("Error loading train schedule: ", e);
            model.addAttribute("schedules", Collections.emptyList());
            model.addAttribute("errorMessage", "Failed to load train schedule: " + e.getMessage());

            if (userDetails != null) {
                model.addAttribute("username", userDetails.getUsername());
            }

            return "schedule/view";
        }
    }
}
