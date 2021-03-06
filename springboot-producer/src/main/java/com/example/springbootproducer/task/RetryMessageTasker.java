package com.example.springbootproducer.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.springbootproducer.constant.Constant;
import com.example.springbootproducer.entity.MessageLog;
import com.example.springbootproducer.entity.Order;
import com.example.springbootproducer.mapper.MessageLogMapper;
import com.example.springbootproducer.producer.RabbitOrderSender;
import com.example.springbootproducer.utils.FastJsonConvertUtils;

/**
 * 定时器任务，定时监测消息的状态
 * @author YE
 *
 */
@Component
public class RetryMessageTasker {
	
	//private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RabbitOrderSender rabbitOrderSender;
	
	@Autowired
	private MessageLogMapper messageLogMapper;
	
	/**
	 * 定时任务每10秒检测一次
	 */
	@Scheduled(initialDelay = 3000,fixedDelay = 10000)
	public void retrySend() {
		//轮询获得状态等于0并且已经超时的消息
		System.out.println("定时任务开始");
		List<MessageLog> list = messageLogMapper.selectMessageByStatus();
		for (MessageLog messageLog : list) {
			//logger.debug("处理消息日志",messageLog);
			if(messageLog.getTryCount() >= Constant.ORDER_TRY_COUNT) {
				System.out.println("重发次数大于3,此时超过最大重发数手动解决问题");
				MessageLog messageLog2 = new MessageLog();
				messageLog2.setMessageId(messageLog.getMessageID());
				messageLog2.setStatus(Constant.ORDER_SEND_FAILURE);
				messageLogMapper.updateMessageStatus(messageLog2);
			}else {
				System.out.println("重发次数+1");
				messageLogMapper.updateCount(messageLog);
				Order order = FastJsonConvertUtils.convertJsonToObject(messageLog.getMessage(), Order.class);
				try {
					rabbitOrderSender.sendOrder(order);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//logger.error("消息发送异常",e);
					System.out.println("消息发送异常");
				}
			}
		}
		
		//logger.debug("重发消息定时任务结束");
		System.out.println("定时任务结束");
	}
	
}
