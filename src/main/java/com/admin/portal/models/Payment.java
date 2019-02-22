 package com.admin.portal.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String paymentCode;
	private Long user_id;
	private Long movie_id;
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public Long getUserId() {
		return user_id;
	}
	public void setUserId(Long userId) {
		this.user_id = userId;
	}
	public Long getMovieId() {
		return movie_id;
	}
	public void setMovieId(Long movieId) {
		this.movie_id = movieId;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public Date getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}
	@CreationTimestamp
	private Date created_date;
	@UpdateTimestamp
	private Date updated_date;
}
