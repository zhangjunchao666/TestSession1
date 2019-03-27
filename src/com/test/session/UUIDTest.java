package com.test.session;

import java.util.UUID;

/**
 * @author zhangjunchao
 * @version 2019年3月27日  上午9:29:58
 * 1.唯一字符串创建的规则： 32位的16进制的字符串
 * 2.用户的机器码+时间戳 拼接生成的
 */



public class UUIDTest {
	public static void main(String[] args) {
		//UUID一般用于订单号
		String string = UUID.randomUUID().toString();
		//String string = uuid.toString();
		System.out.println(string.replace("-", ""));
		
		//时间戳
		long timeMillis = System.currentTimeMillis();
		System.out.println(timeMillis);
		
		//生成4为随机数   1000控制位数
		int i = (int)((Math.random()*9+1)*1000);
		System.out.println(i);
	}
	
}
