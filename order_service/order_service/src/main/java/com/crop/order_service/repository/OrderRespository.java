package com.crop.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crop.order_service.entity.Order;

public interface OrderRespository extends JpaRepository<Order,Long> {

}
