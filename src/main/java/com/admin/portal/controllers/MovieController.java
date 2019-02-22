package com.admin.portal.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.admin.portal.assemblers.MovieAssembler;
import com.admin.portal.exceptions.MovieNotFounfException;
import com.admin.portal.models.Movie;
import com.admin.portal.repositories.MovieRepository;
@RestController
public class MovieController {

	@Autowired
	private MovieRepository movieRespository;;
	
	@Autowired
	private MovieAssembler movieAssembler;
	
	
	  @GetMapping("/movies/{id}")
	    public Resource<Movie> getMovie(@PathVariable Long id) {
	    	

	    	Movie movie =  movieRespository.findById(id)
	    			.orElseThrow(() -> new MovieNotFounfException(id) );
	    	return movieAssembler.toResource(movie);
	    }
	
	
	  @GetMapping("/movies")
	    public Resources<Resource<Movie>> all() {
	    	
	    	List<Resource<Movie>> movies = movieRespository.findAll().stream()
	    									.map(movieAssembler::toResource)
	    									.collect(Collectors.toList());
	    	return new Resources<Resource<Movie>>(movies,
	    			ControllerLinkBuilder.linkTo(methodOn(MovieController.class).all()).withSelfRel());
	    }
	  
	  @PostMapping("/add/movie")
		ResponseEntity<?> newUser(@RequestBody Movie newMovie) throws URISyntaxException {

			Resource<Movie> resource = movieAssembler.toResource(movieRespository.save(newMovie));

			return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
		}
	    
	    @DeleteMapping("/movie/{id}")
		ResponseEntity<?> deleteMovie(@PathVariable Long id) {

	    	movieRespository.deleteById(id);
			
			return ResponseEntity.noContent().build();
		}
	
}
