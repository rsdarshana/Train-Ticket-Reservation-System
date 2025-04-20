package com.trainsystem.controller;

import com.trainsystem.model.BookedSeat;
import com.trainsystem.model.BookingDetailsDTO;
import com.trainsystem.model.Seat;
import com.trainsystem.model.TrainSchedule;
import com.trainsystem.repository.BookedSeatRepository;
import com.trainsystem.repository.SeatRepository;
import com.trainsystem.repository.TrainScheduleRepository;
import com.trainsystem.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {
    
    private final UserService userService;

    private final BookedSeatRepository bookedSeatRepository;

    private final TrainScheduleRepository trainScheduleRepository;

    private final SeatRepository seatRepository;
    
    public DashboardController(UserService userService,
                               BookedSeatRepository bookedSeatRepository,
                               TrainScheduleRepository trainScheduleRepository,
                               SeatRepository seatRepository) {
        this.userService = userService;
        this.bookedSeatRepository = bookedSeatRepository;
        this.trainScheduleRepository = trainScheduleRepository;
        this.seatRepository = seatRepository;
    }
    
    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        model.addAttribute("username", username);
        return "dashboard";
    }

    @GetMapping("/bookings")
    public String viewBookings(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        List<BookedSeat> bookings = bookedSeatRepository.findByUsername(username);
        List<BookingDetailsDTO> bookingDetailsDTOList = new ArrayList<>();

        for(BookedSeat bookedSeat: bookings){
            TrainSchedule schedule = trainScheduleRepository.findById(Long.valueOf(bookedSeat.getTrainScheduleId())).orElseThrow();
            Seat seat = seatRepository.getById(bookedSeat.getSeatId());
            BookingDetailsDTO bookingDetailsDTO =
                    new BookingDetailsDTO(bookedSeat.getId(), bookedSeat.getBookingTime(),
                        bookedSeat.getStatus(), seat.getCoachNumber(), seat.getSeatNumber(), seat.getCoachType(),
                            seat.getSeatType(), schedule.getId(), schedule.getTrain().getName(),
                            schedule.getTrain().getTrainNumber(),
                            schedule.getSourceStation(),
                            schedule.getDestinationStation(), schedule.getDepartureTime(),
                            schedule.getArrivalTime(), schedule.getDurationMinutes(), seat.getId());
            bookingDetailsDTOList.add(bookingDetailsDTO);
        }

        model.addAttribute("bookings", bookingDetailsDTOList);
        return "booking/bookings"; // should match bookings.html in templates
    }

}