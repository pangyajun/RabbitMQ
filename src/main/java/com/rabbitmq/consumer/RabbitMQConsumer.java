package com.rabbitmq.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Repository;

import com.oaec.entity.Product;
import com.rabbitmq.client.Channel;
import com.utils.RSAUtils;
import com.utils.SerializeUtils;

@Repository("rabbitMQConsumer")
public class RabbitMQConsumer implements MessageListener{

	
	String publicKeyStr="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKI0vtr5EN+P2vI6MEOkxiW1qLX8cWJ1GM0e9JiIJBWv4ACeJfvm6FNkK1s3m+m8RwPHSPwmcxAhjpqZu1iKx1RkgNh2ccpsqywyFWi6jH4NnHVb82OOvoWGqN0UkStbSJI26D4xespjdG695G2qADuyX33/I73dTTnbQersaViwIDAQAB";
	@SuppressWarnings("unused")
	private void getBoys(Message message) {
		System.out.println(12155565);
		byte[] body = message.getBody();
		HashMap<String,List<Product>> object =  SerializeUtils.deserialize(body);
		System.out.println(object );

	}
	@SuppressWarnings("unused")
	private void getLove(Message message) {
		System.out.println(12155565);
		byte[] body = message.getBody();
		HashMap<String,List<Product>> object =   SerializeUtils.deserialize(body);
		System.out.println(object );

	}
	@SuppressWarnings("unused")
	private void getGirls(Message message) {
		byte[] body = message.getBody();
		HashMap<String,List<Product>> object = SerializeUtils.deserialize(body);
		System.out.println(object );
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message) {
		/*try {
			byte[] messageBody = message.getBody();
			byte[] decryptByPublicKey = RSAUtils.decryptByPublicKey(messageBody, publicKeyStr,"RSA");
			HashMap<String,Object> object = SerializeUtils.deserialize(decryptByPublicKey);
			String sign = (String) object.get("sign"); 
			ArrayList<Product> object3 = (ArrayList<Product>) object.get("productList");
			boolean verify = RSAUtils.verify(object3, publicKeyStr, sign, "RSA", "utf-8");
			System.out.println(verify==true?"验签成功":"验签失败");
			System.out.println(object3.get(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
