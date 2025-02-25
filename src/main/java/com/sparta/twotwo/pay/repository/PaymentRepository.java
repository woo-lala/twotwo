package com.sparta.twotwo.pay.repository;

import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.pay.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Payment findByOrder(Order order);
}
