package com.crop.inventory_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="Inventory")
@Builder
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Inventory {
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	private Long productId;
	private Long AvailableQuantity;
	private Long reserverdQuantity;
	private LocalDateTime updatedAt;
	
	@PreUpdate
	public void PostUpdate() {
		this.updatedAt = LocalDateTime.now();
		//this.registeredDate = LocalDate.now();
	}

	public Inventory() {
		// TODO Auto-generated constructor stub
	}
	
	

}
