package com.admin.portal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.admin.portal.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	Payment getPaymentByPaymentCode(String paymentCode);
	
	@Query("select p from Payment p where p.user_id = :userid and p.movie_id = :movieid order by p.created_date DESC ")
	List<Payment> findFirstByUser_idAndMovie_id(@Param("userid") Long userId,@Param("movieid") Long movieId);
}
