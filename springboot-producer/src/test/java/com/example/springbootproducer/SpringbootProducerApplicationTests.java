package com.example.springbootproducer;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springbootproducer.entity.Order;
import com.example.springbootproducer.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootProducerApplicationTests {

	/*@Test
	public void contextLoads() {
	}*/
	
	/*@Autowired
	private Sender sender;
	
	@Test
	public void demo1() {
		Order order = new Order();
		order.setId("15");
		order.setName("小强");
		order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
		
		sender.sendTest(order);
	}*/
	
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 测试发送消息
	 */
	@Test
	public void reliableTest() {
		Order order = new Order();
		order.setId("2018102300000006");
		order.setMessageId(UUID.randomUUID().toString()+"&"+System.currentTimeMillis());
		order.setName("测试的消息数据");
		orderService.creatOrder(order);
	}

}