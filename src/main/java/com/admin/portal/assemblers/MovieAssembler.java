package com.admin.portal.assemblers;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import com.admin.portal.controllers.MovieController;
import com.admin.portal.controllers.UserController;
import com.admin.portal.models.Movie;
import com.admin.portal.models.User;

@Component
public class MovieAssembler implements ResourceAssembler<Movie, Resource<Movie>> {

	@Override
	public Resource<Movie> toResource(Movie entity) {
		// TODO Auto-generated method stub
//		return new Resource<User>(entity, linkTo(methodOn))
//		Link link = linkTo();
		return new Resource<Movie>(entity,
				 ControllerLinkBuilder.linkTo(methodOn(MovieController.class).getMovie(entity.getId())).withSelfRel(),
				 ControllerLinkBuilder.linkTo(methodOn(MovieController.class).all()).withRel("movies"));
	}

}
