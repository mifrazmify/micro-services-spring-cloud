package io.mify.os.api.service;

import io.mify.os.api.common.Payment;
import io.mify.os.api.common.TransactionRequest;
import io.mify.os.api.common.TransactionResponse;
import io.mify.os.api.entity.Order;
import io.mify.os.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public TransactionResponse saveOrder(TransactionRequest request) {
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        // rest call
        Payment paymentResponse = restTemplate.postForObject("http://localhost:9191/payment/doPayment", payment, Payment.class);
        String response = paymentResponse.getPaymentStatus().equals("success") ? "payment processing successful and order placed" : "There is a failure in payment api, order canceled";
        orderRepository.save(order);

        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
    }
}
