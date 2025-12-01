package com.flightapp.emailservice.consumer;

import com.flightapp.emailservice.config.RabbitMQConfig;
import com.flightapp.emailservice.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailSenderService emailSenderService;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consumeEmailMessage(String messageJson) {
        System.out.println("📥 Received email payload: " + messageJson);

        String[] parts = messageJson.split("\\|");

        String email = parts[0];
        String pnr = parts[1];
        String status = parts[2];

        String subject = "Your Booking Status - " + status;
        String body = "Your booking is " + status + ".\nPNR: " + pnr;

        emailSenderService.sendEmail(email, subject, body);
    }
}
