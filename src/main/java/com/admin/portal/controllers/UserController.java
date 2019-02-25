package com.admin.portal.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.admin.portal.DTO.PaymentDTO;
import com.admin.portal.assemblers.UserAssembler;
import com.admin.portal.exceptions.UserNotFoundException;
import com.admin.portal.models.Movie;
import com.admin.portal.models.Payment;
import com.admin.portal.models.User;
import com.admin.portal.repositories.MovieRepository;
import com.admin.portal.repositories.PaymentRepository;
import com.admin.portal.repositories.UserRepository;

@RestController
public class UserController {
	
	
	@Autowired
	private UserRepository userRespository;
	
	@Autowired
	private UserAssembler userAssembler;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	public UserController(UserRepository repository, UserAssembler assembler, PaymentRepository paymentRepository, MovieRepository movieRepository) {
		this.userRespository = repository;
		this.userAssembler =assembler;
		this.paymentRepository = paymentRepository;
		this.movieRepository = movieRepository;
	}
	
    @GetMapping("/users/{id}")
    public Resource<User> getUser(@PathVariable Long id) {
    	
    	User u =  userRespository.findById(id)
    			.orElseThrow(() -> new UserNotFoundException(id) );
    	return userAssembler.toResource(u);
    }
    
    @GetMapping("/users")
    public Resources<Resource<User>> all(Pageable p) {
    	
    	
    	List<Resource<User>> users = userRespository.findAll(p).stream()
    									.map(userAssembler::toResource)
    									.collect(Collectors.toList());
    	return new Resources<Resource<User>>(users,
    			ControllerLinkBuilder.linkTo(methodOn(UserController.class).all(p)).withSelfRel());
    }
    
    @GetMapping("/users/pagination")
    public Page<User> allP(Pageable p) {
      	Page<User> users = userRespository.findAll(p);
    	
    	return users;
    }
    
    @PostMapping("/add/user")
	ResponseEntity<?> newUser(@RequestBody User newUser) throws URISyntaxException {

		Resource<User> resource = userAssembler.toResource(userRespository.save(newUser));

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}
    
    @DeleteMapping("/users/{id}")
	ResponseEntity<?> deleteUser(@PathVariable Long id) {

		userRespository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
    
    @GetMapping("/user/payments/{id}")
    public Set<Payment> getPaymentsByUser(@PathVariable Long id) {
    	
    	User u =  userRespository.findById(id)
    			.orElseThrow(() -> new UserNotFoundException(id) );
    	return u.getPayments();
    }
    
    @GetMapping("/user/{id}/payments")
    public Payment getPaymentByPaymentCode(@PathVariable Long id, @RequestParam String paymentCode) {
    	
    	User u =  userRespository.findById(id)
    			.orElseThrow(() -> new UserNotFoundException(id) );
    	Payment payment = new Payment();
    	for(Payment p : u.getPayments()) {
    		if( paymentCode != null && paymentCode.equals(p.getPaymentCode()) ) {
    			payment = p;
    		}
    	}
    	return payment;
    }
    
    @GetMapping("/user/sortedPayments")
    public List<PaymentDTO> getPayments() {
    	
    	List<PaymentDTO> response = new ArrayList<PaymentDTO>();
    	
    	List<User> users = userRespository.findAll();
    	List<Movie> movies = movieRepository.findAll();
    	
    	for(User u : users) {
    		for(Movie m : movies) {
    			PaymentDTO dto = new PaymentDTO();
    			List<Payment> ps =	paymentRepository.findFirstByUser_idAndMovie_id(u.getId(), m.getId());
    			Payment p = ps.get(0);
    			if(p != null) {
    				dto.setDate(p.getCreated_date());
    				dto.setMovieName(m.getName());
    				dto.setPaymentCode(p.getPaymentCode());
    				dto.setUserName(u.getName());
    				response.add(dto);
    			}
    		}
    	}
    	
    	return response;
    }
	
}
