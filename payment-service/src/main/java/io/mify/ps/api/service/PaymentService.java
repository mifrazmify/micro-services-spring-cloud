package io.mify.ps.api.service;

import io.mify.ps.api.entity.Payment;
import io.mify.ps.api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment) {

        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public String paymentProcessing() {
        // this should be 3rd party payment gateway
        return new Random().nextBoolean() ? "success" : "false";
    }
}
