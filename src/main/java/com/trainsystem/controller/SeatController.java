package com.trainsystem.controller;

import com.trainsystem.model.*;
import com.trainsystem.repository.BookedSeatRepository;
import com.trainsystem.repository.SeatRepository;
import com.trainsystem.repository.TrainScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seats")
public class SeatController {
    private final TrainScheduleRepository trainScheduleRepository;
    private final SeatRepository seatRepository;
    private final BookedSeatRepository bookedSeatRepository;

    public SeatController(TrainScheduleRepository trainScheduleRepository,
                          SeatRepository seatRepository,
                          BookedSeatRepository bookedSeatRepository) {
        this.trainScheduleRepository = trainScheduleRepository;
        this.seatRepository = seatRepository;
        this.bookedSeatRepository = bookedSeatRepository;
    }

    @GetMapping("/{scheduleId}")
    public String showSeatSelection(@PathVariable Long scheduleId, Model model) {
        Optional<TrainSchedule> schedule = trainScheduleRepository.findById(scheduleId);
        if (schedule.isEmpty()) {
            return "redirect:/schedule/view";
        }

        model.addAttribute("schedule", schedule.get());
        model.addAttribute("coachTypes", Seat.CoachType.values());
        model.addAttribute("seatTypes", Seat.SeatType.values());
        return "seats/selection";
    }

    @PostMapping("/available")
    public String showAvailableSeats(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestParam Long scheduleId,
                                     @RequestParam Seat.CoachType coachType,
                                     @RequestParam Seat.SeatType seatType,
                                     Model model) {
        TrainSchedule schedule = trainScheduleRepository.findById(scheduleId).orElseThrow();
        List<Seat> availableSeats = seatRepository.findByTrainScheduleAndCoachTypeAndSeatTypeAndIsBookedFalse(
                schedule, coachType, seatType);

        model.addAttribute("schedule", schedule);
        model.addAttribute("availableSeats", availableSeats);
        model.addAttribute("selectedCoachType", coachType);
        model.addAttribute("selectedSeatType", seatType);

        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }

        return "seats/available";
    }

    @PostMapping("/book")
    public String bookSeats(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam Long scheduleId,
                            @RequestParam List<Long> seatIds,
                            @RequestParam String coachNumber,
                            @RequestParam Long seatNumber,
                            @RequestParam String seatType,
                            Model model) {

        TrainSchedule schedule = trainScheduleRepository.findById(scheduleId).orElseThrow();

        List<Seat> seats = seatRepository.findAllById(seatIds);
        Double aggregatedAmt = seats.stream().mapToDouble(Seat::getFare).sum();

        model.addAttribute("scheduleId", scheduleId);
        model.addAttribute("seatIds", seatIds);
        model.addAttribute("coachNumber", coachNumber);
        model.addAttribute("seatNumber", seatNumber);
        model.addAttribute("seatType", seatType);
        model.addAttribute("trainNumber", schedule.getTrain().getTrainNumber());
        model.addAttribute("source", schedule.getSourceStation());
        model.addAttribute("destination", schedule.getDestinationStation());
        model.addAttribute("departureTime", schedule.getDepartureTime());
        model.addAttribute("arrivalTime", schedule.getArrivalTime());
        model.addAttribute("aggregatedAmt", aggregatedAmt);
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "payment/payment";
    }

    @PostMapping("/confirm-booking")
    public String confirmBooking(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam String scheduleId,
                                 @RequestParam List<Long> seatIds,
                                 @RequestParam String paymentMethod,
                                 @RequestParam String username,
                                 @RequestParam String trainNumber,
                                 @RequestParam String coachNumber,
                                 @RequestParam Long seatNumber,
                                 @RequestParam String seatType,
                                 Model model) {

        TrainSchedule schedule = trainScheduleRepository.findById(Long.valueOf(scheduleId)).orElseThrow();
        List<Seat> seatsToBook = seatRepository.findAllById(seatIds);

        for (Seat seat : seatsToBook) {
            if (!seat.isBooked()) {
                seat.setBooked(true);
            }
        }
        seatRepository.saveAll(seatsToBook);

        for (Seat seat : seatsToBook) {
            BookedSeat bookedSeat = new BookedSeat(
                    username,
                    seat.getId(),
                    schedule.getId(),
                    seat.getFare()
            );
            bookedSeatRepository.save(bookedSeat);
        }

        model.addAttribute("username", username);
        model.addAttribute("seatCount", seatsToBook.size());
        model.addAttribute("paymentMethod", paymentMethod);
        model.addAttribute("scheduleId", scheduleId);
        model.addAttribute("coachNumber", coachNumber);
        model.addAttribute("seatNumber", seatNumber);
        model.addAttribute("seatType", seatType);
        model.addAttribute("trainNumber", trainNumber);
        model.addAttribute("source", schedule.getSourceStation());
        model.addAttribute("destination", schedule.getDestinationStation());
        model.addAttribute("departureTime", schedule.getDepartureTime());
        model.addAttribute("arrivalTime", schedule.getArrivalTime());

        return "payment/payment_success";
    }

    @PostMapping("/cancel")
    public String cancelBooking(@RequestParam Long bookingId,
                                @RequestParam Long seatId,
                                @RequestParam Long scheduleId,
                                @RequestParam String coachNumber,
                                @RequestParam String seatNumber,
                                Model model) {

        Optional<BookedSeat> bookedSeatOpt = bookedSeatRepository.findById(bookingId);
        if (bookedSeatOpt.isPresent()) {
            BookedSeat bookedSeat = bookedSeatOpt.get();
            bookedSeat.setStatus("CANCELLED");
            bookedSeatRepository.save(bookedSeat);
        }

        Optional<Seat> seatOpt = seatRepository.findById(seatId);
        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            seat.setBooked(false);
            seatRepository.save(seat);
        }

        Optional<TrainSchedule> scheduleOpt = trainScheduleRepository.findById(scheduleId);
        if (scheduleOpt.isPresent()) {
            TrainSchedule schedule = scheduleOpt.get();
            schedule.setAvailableSeats(schedule.getAvailableSeats() + 1);
            trainScheduleRepository.save(schedule);
        }

        model.addAttribute("successMessage", "Booking #" + bookingId + " for seat " +
                coachNumber + "/" + seatNumber + " has been cancelled successfully.");

        return "redirect:/bookings";
    }
}
