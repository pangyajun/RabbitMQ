package com.rabbitmq.consumer;

import java.util.HashMap;
import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oaec.entity.Product;
import com.utils.SerializeUtil;

@Repository("rabbitMQConsumer")
public class RabbitMQConsumer implements MessageListener{

	@SuppressWarnings("unused")
	
	private void getBoys(Message message) {
		byte[] body = message.getBody();
		HashMap<String,List<Product>> object = (HashMap<String,List<Product>>) SerializeUtil.toObject(body);
		System.out.println(object );

	}
	@SuppressWarnings("unused")
	private void getLove(Message message) {
		byte[] body = message.getBody();
		HashMap<String,List<Product>> object = (HashMap<String,List<Product>>) SerializeUtil.toObject(body);
		System.out.println(object );

	}
	@SuppressWarnings("unused")
	private void getGirls(Message message) {
		byte[] body = message.getBody();
		HashMap<String,List<Product>> object = (HashMap<String,List<Product>>) SerializeUtil.toObject(body);
		System.out.println(object );
	}
	
	@Override
	public void onMessage(Message message) {
		byte[] body = message.getBody();
		@SuppressWarnings("unchecked")
		HashMap<String,List<Product>> object = (HashMap<String,List<Product>>) SerializeUtil.toObject(body);
		System.out.println(object );
	}
}
