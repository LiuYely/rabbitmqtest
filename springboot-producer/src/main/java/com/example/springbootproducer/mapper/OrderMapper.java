package com.example.springbootproducer.mapper;

import com.example.springbootproducer.entity.Order;


public interface OrderMapper {

	//新增订单/消息
	void insertOrder(Order order);
}