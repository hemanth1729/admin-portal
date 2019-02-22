package com.admin.portal.assemblers;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import com.admin.portal.controllers.UserController;
import com.admin.portal.models.User;

@Component
public class UserAssembler implements ResourceAssembler<User, Resource<User>> {

	@Override
	public Resource<User> toResource(User entity) {
		// TODO Auto-generated method stub
//		return new Resource<User>(entity, linkTo(methodOn))
//		Link link = linkTo();
		Pageable p =PageRequest.of(1, 1);
		return new Resource<User>(entity,
				 ControllerLinkBuilder.linkTo(methodOn(UserController.class).getUser(entity.getId())).withSelfRel(),
				 ControllerLinkBuilder.linkTo(methodOn(UserController.class).all(p)).withRel("users"));
	}

}
