package com.rabbitmq.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.product.RabbitMQProduct;

@Controller
public class RabbitMQController {
	
	@Resource
	private RabbitMQProduct rabbitMQProduct;
	
	@RequestMapping("mq")
	@ResponseBody
	public String sendMessage(HttpServletResponse rsesponse) {
		return rabbitMQProduct.sendMessage();
		 
		
		
	}
	
	public static void main(String[] args) {

       
	}
}
