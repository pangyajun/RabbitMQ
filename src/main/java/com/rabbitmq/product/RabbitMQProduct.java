package com.rabbitmq.product;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.oaec.entity.Product;

@Service
public class RabbitMQProduct {

	@Resource
    private AmqpTemplate amqpTemplate;
	
	public String sendMessage() {
		
		try {
			//amqpTemplate.convertSendAndReceive( "boys.love.girls", "hello RabbitMQ");
			Product product=new Product("1","iphone8",5688.0);
			Product product2=new Product("2","Galaxy",6188.0);
			ArrayList<Product> list = new ArrayList<>();
			HashMap<String,ArrayList<Product>> map=new HashMap<>();
			list.add(product);
			list.add(product2);
			map.put("productList", list);
			amqpTemplate.convertAndSend( "boys.love.girls", map);	
			return "success";
		} catch (Exception e) {
			return "参数或mq服务器有误";
		}
			
	}
}
