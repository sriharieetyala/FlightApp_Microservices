package com.flightapp.bookingservice.controller;

import com.flightapp.bookingservice.dto.request.BookingRequest;
import com.flightapp.bookingservice.dto.response.BookingResponse;
import com.flightapp.bookingservice.entity.Booking;
import com.flightapp.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    @PostMapping
    public ResponseEntity<BookingResponse> bookTicket(@Valid @RequestBody BookingRequest request) {
        Booking booking = service.bookTicket(request);
        String pnr = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BookingResponse(booking.getId(), pnr));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Booking>> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getBookingsByEmail(email));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Booking> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getBookingById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Integer id) {
        service.cancelBooking(id);
        return ResponseEntity.ok("Booking cancelled successfully");
    }
}
