package com.admin.portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.portal.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
