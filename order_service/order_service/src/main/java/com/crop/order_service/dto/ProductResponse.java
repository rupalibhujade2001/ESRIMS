package com.crop.order_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

//import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record ProductResponse(Long id,

		String email,

		Long price,

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
