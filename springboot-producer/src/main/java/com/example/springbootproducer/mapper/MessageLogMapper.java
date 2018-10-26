package com.example.springbootproducer.mapper;

import java.util.List;

import com.example.springbootproducer.entity.MessageLog;

public interface MessageLogMapper {

	//更新消息的状态
	void updateMessageStatus(MessageLog messageLog);
	
	//插入消息
	void insertMessageLog(MessageLog messageLog);
	
	//查询消息
	List<MessageLog> selectMessageByStatus();
	
	//更新重发次数
	void updateCount(MessageLog messageLog);
}
