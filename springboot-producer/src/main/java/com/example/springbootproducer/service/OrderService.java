package com.example.springbootproducer.service;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springbootproducer.constant.Constant;
import com.example.springbootproducer.entity.MessageLog;
import com.example.springbootproducer.entity.Order;
import com.example.springbootproducer.mapper.MessageLogMapper;
import com.example.springbootproducer.mapper.OrderMapper;
import com.example.springbootproducer.producer.RabbitOrderSender;
import com.example.springbootproducer.utils.FastJsonConvertUtils;

/**
 * 生成订单消息，将数据及消息入库
 * @author YE
 *
 */
@Service
public class OrderService {

	@Autowired
	private RabbitOrderSender rabbitOrderSender;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private MessageLogMapper messageLogMapper;
	
	public void creatOrder(Order order) {
		//获取当前时间
		Date date = new Date();
		System.out.println("获取消息入库的当前时间"+date);
		//订单消息入库
		orderMapper.insertOrder(order);
		//设置消息日志对象
		MessageLog messageLog = new MessageLog();
		messageLog.setCreateTime(date);
		messageLog.setMessage(FastJsonConvertUtils.convertObjectToJson(order));
		messageLog.setMessageId(order.getMessageId());
		messageLog.setNextRetry(DateUtils.addMilliseconds(date, Constant.ORDER_TIMEOUT));
		messageLog.setStatus(Constant.ORDER_SENDING);
		messageLog.setTryCount(0);
		messageLogMapper.insertMessageLog(messageLog);
		rabbitOrderSender.sendOrder(order);
		
	}
}