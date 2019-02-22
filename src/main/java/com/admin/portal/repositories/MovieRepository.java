package com.admin.portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.portal.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
