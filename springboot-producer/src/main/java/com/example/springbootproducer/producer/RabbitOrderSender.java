package com.example.springbootproducer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springbootproducer.constant.Constant;
import com.example.springbootproducer.entity.MessageLog;
import com.example.springbootproducer.entity.Order;
import com.example.springbootproducer.mapper.MessageLogMapper;
/**
 * 发送消息并设置回调函数
 * @author YE
 *
 */
@Component
public class RabbitOrderSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private MessageLogMapper messageLogMapper;
	
	/**
	 * 回调函数
	 */
	private final RabbitTemplate.ConfirmCallback confirmCallback = new ConfirmCallback() {
		
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			// TODO Auto-generated method stub
			//获取消息唯一标识
			String messageId = correlationData.getId();
			//接收到消费者的确认信息后根据消息唯一标识更新消息的状态
			if(ack) {
				MessageLog messageLog = new MessageLog();
				messageLog.setMessageId(messageId);
				System.out.println("收到确认信息，消息ID为"+messageId);
				messageLog.setStatus(Constant.ORDER_SEND_SUCCESS);
				messageLogMapper.updateMessageStatus(messageLog);
			}else {
				//对于不同使用场景有不同的处理方式，可以是重试，这里只是测试
				System.out.println("未收到确认消息，异常！！！");
			}
		}
	};
	
	/**
	 * 发送消息
	 * @param order
	 */
	public void sendOrder(Order order) {
		//设置监听回调函数
		rabbitTemplate.setConfirmCallback(confirmCallback);
		CorrelationData correlationData = new CorrelationData(order.getMessageId());
		rabbitTemplate.convertAndSend("order_exchange", "order.a", order, correlationData);
	}
}
