package com.admin.portal.controllers;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.portal.exceptions.MovieNotFounfException;
import com.admin.portal.exceptions.UserNotFoundException;
import com.admin.portal.models.Movie;
import com.admin.portal.models.Payment;
import com.admin.portal.models.User;
import com.admin.portal.repositories.MovieRepository;
import com.admin.portal.repositories.PaymentRepository;
import com.admin.portal.repositories.UserRepository;

@RestController
public class PaymentsController {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	 @PostMapping("/createPayment")
		Payment createPayment(@RequestBody Payment payment) throws URISyntaxException {
		 
		 System.out.println(payment.getUserId()+"||"+ payment.getMovieId());
		 
		 User user = userRepository.findById(payment.getUserId())
				 	.orElseThrow(() -> new UserNotFoundException(payment.getUserId()));
		 
		 Movie movie = movieRepository.findById(payment.getMovieId())
				 .orElseThrow(() -> new MovieNotFounfException(payment.getMovieId()));
		 
		 addPaymentCode(payment);

			Payment newPayment = paymentRepository.save(payment);

			return newPayment;
		}

	private void addPaymentCode(Payment payment) {
		
		
		
	}

}
