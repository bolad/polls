package com.bolad.polls.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass //allow an entity to inherit properties from a base class.
@JsonIgnoreProperties(
		value = {"createdBy", "updatedBy"},
		allowGetters = true /*It means the specified logical properties in 
		@JsonIgnoreProperties will take part in JSON serialization but not in deserialization.*/ 
)
public abstract class UserDateAudit extends DateAudit {
	@CreatedBy
	@Column(updatable = false)
	private Long createdBy;
	
	@LastModifiedBy
	private Long updatedBy;

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
