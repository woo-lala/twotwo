package com.sparta.twotwo.pay.service;


import com.sparta.twotwo.auth.util.SecurityUtil;
import com.sparta.twotwo.enums.PayMethod;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.order.entity.Order;
import com.sparta.twotwo.pay.dto.PaymentDto;
import com.sparta.twotwo.pay.entity.Payment;
import com.sparta.twotwo.pay.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;


    public Payment save(Order order, Member member) {

        Payment pay = Payment.builder()
                .approvedAt(LocalDateTime.now())
                .method(PayMethod.CARD)
                .order(order)
                .member(member)
                .build();


        return paymentRepository.save(pay);
    }

    public void delete(Order order){
        Payment payment = paymentRepository.findByOrder(order);

        payment.setDeletedAt(LocalDateTime.now());
        payment.setDeletedBy(SecurityUtil.getMemberIdFromSecurityContext());

        paymentRepository.flush();
    }


}
