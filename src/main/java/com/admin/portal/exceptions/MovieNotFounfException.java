package com.admin.portal.exceptions;

public class MovieNotFounfException extends RuntimeException {
	
	public MovieNotFounfException(Long id){
		super("could not find Movie with id "+ id);
	}

}
