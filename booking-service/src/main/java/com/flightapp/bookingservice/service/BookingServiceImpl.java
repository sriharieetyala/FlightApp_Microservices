package com.flightapp.bookingservice.service;

import com.flightapp.bookingservice.dto.request.BookingRequest;
import com.flightapp.bookingservice.dto.response.FlightResponse;
import com.flightapp.bookingservice.entity.Booking;
import com.flightapp.bookingservice.exception.BookingInvalidException;
import com.flightapp.bookingservice.exception.BookingNotFoundException;
import com.flightapp.bookingservice.feign.FlightServiceClient;
import com.flightapp.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repo;
    private final FlightServiceClient flightClient;

    private String generatePNR() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public Booking bookTicket(BookingRequest request) {

        // Get flight from Flight Service
        FlightResponse flight = flightClient.getFlightById(request.getFlightId());
        if (flight == null) {
            throw new BookingInvalidException("Flight not found");
        }

        if (request.getNumberOfTickets() > flight.getSeatsAvailable()) {
            throw new BookingInvalidException("Not enough seats available");
        }

        Booking booking = Booking.builder()
                .flightId(request.getFlightId())
                .passengerName(request.getPassengerName())
                .age(request.getAge())
                .gender(request.getGender())
                .meal(request.getMeal())
                .email(request.getEmail())
                .numberOfTickets(request.getNumberOfTickets())
                .status("BOOKED")
                .build();

        repo.save(booking);
        return booking;
    }

    @Override
    public Booking getBookingById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
    }

    @Override
    public List<Booking> getBookingsByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public Booking cancelBooking(Integer id) {
        Booking booking = repo.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        // get flight departure
        FlightResponse flight = flightClient.getFlightById(booking.getFlightId());

        if (Duration.between(LocalDateTime.now(), flight.getDepartureTime()).toHours() < 24) {
            throw new BookingInvalidException("Cannot cancel less than 24 hours before departure");
        }

        booking.setStatus("CANCELLED");
        repo.save(booking);
        return booking;
    }
}
