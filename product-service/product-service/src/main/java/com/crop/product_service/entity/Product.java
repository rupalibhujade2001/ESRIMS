package com.crop.product_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "product")
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email
	@Column(nullable = false,unique=true)
	private String email;
	@Column(nullable = false)
	private Long price;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String category;
	@Column(nullable = false, length = 2000)
	private String description;
	private String imageUrl;
	private Boolean active;
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
	private Double offerPercentage;
	private LocalDate registeredDate;

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.registeredDate = LocalDate.now();
		this.active = true;
	}

	@PreUpdate
	public void PostUpdate() {
		this.updateAt = LocalDateTime.now();
		//this.registeredDate = LocalDate.now();
		this.active = true;
	}

}
