package com.example.springbootproducer.constant;

/**
 * 常量信息
 * @author YE
 *
 */
public class Constant {

	public static final String ORDER_SENDING = "0"; //发送中
    
    public static final String ORDER_SEND_SUCCESS = "1"; //成功
     
    public static final String ORDER_SEND_FAILURE = "2"; //失败
    
    public static final Integer ORDER_TRY_COUNT = 3; //重发次数
     
    public static final int ORDER_TIMEOUT = 10000; //超时单位：毫秒

}
