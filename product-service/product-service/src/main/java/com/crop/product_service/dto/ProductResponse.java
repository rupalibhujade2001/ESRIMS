package com.crop.product_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

//import jakarta.validation.constraints.NotBlank;

public record ProductResponse(Long id,

		String email,

	//	String farmerName,

		Long price,

//		String contact,

		String name,

		String category,

		String description,

		String imageUrl,

		Boolean active,

		Double offerPercentage,

		LocalDate registeredDate,

		LocalDateTime createdAt,

		LocalDateTime updateAt) {

}
