package com.admin.portal.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.admin.portal.assemblers.UserAssembler;
import com.admin.portal.exceptions.UserNotFoundException;
import com.admin.portal.models.Payment;
import com.admin.portal.models.User;
import com.admin.portal.repositories.UserRepository;

@RestController
public class UserController {
	
	
	@Autowired
	private UserRepository userRespository;
	
	@Autowired
	private UserAssembler userAssembler;
	
	public UserController(UserRepository repository, UserAssembler assembler) {
		this.userRespository = repository;
		this.userAssembler =assembler;
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
    public Set<Payment> getPaymentByPaymentCode(@PathVariable Long id) {
    	
    	User u =  userRespository.findById(id)
    			.orElseThrow(() -> new UserNotFoundException(id) );
    	return u.getPayments();
    }
	
}
