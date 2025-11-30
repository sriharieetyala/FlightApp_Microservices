package com.flightapp.bookingservice.feign;

import com.flightapp.bookingservice.dto.response.FlightResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-service")
public interface FlightServiceClient {

    @GetMapping("/flights/{id}")
    FlightResponse getFlightById(@PathVariable("id") Integer id);
}
