package com.example.springbootproducer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springbootproducer.entity.Order;

@Component
public class Sender {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendTest(Order order) {
		
		CorrelationData correlationData = new CorrelationData();
		correlationData.setId(order.getMessageId());
		
		rabbitTemplate.convertAndSend("test_exchange", //路由交换机
				"test.key", //路由Key
				order, //内容
				correlationData); //消息唯一ID
	}
}
