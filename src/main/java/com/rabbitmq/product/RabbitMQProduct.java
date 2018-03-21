package com.rabbitmq.product;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.oaec.entity.Product;
import com.oaec.entity.Spec;
import com.utils.RSAUtils;
import com.utils.SerializeUtils;

@Service
public class RabbitMQProduct {

	@Resource
    private AmqpTemplate amqpTemplate;
	
	public String sendMessage() {
		String privateKeyStr="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIojS+2vkQ34/a8jowQ6TGJbWotfxxYnUYzR70mIgkFa/gAJ4l++boU2QrWzeb6bxHA8dI/CZzECGOmpm7WIrHVGSA2HZxymyrLDIVaLqMfg2cdVvzY46+hYao3RSRK1tIkjboPjF6ymN0br3kbaoAO7Jfff8jvd1NOdtB6uxpWLAgMBAAECgYAjvVYbvNzOvKkF8zZuFZdFq1UV0kX0GWAkCvzaDZOFzUECSYxkMSTvGkbQNAs/oZWFZF7UAvwn1d8UNWmv4hicLae2tPL1JFu7F9b/vWg87q6uh0K4WpbIqNkL4zhSSYLfvr3NP4UsS2T8HA0O0Fi8flGtSsCC8spFUW8XZg/ioQJBAMDy6fRtln5wkc44GU0KQKS2qwhwhdFSPKQ1qR/tnmKg5dgWlvF4chy8Vxuf0beIEi2YPHAQkckYZi0u+OWhH18CQQC3Ry7betnuXOo/lXuAkTTv/ToLtAgxvnXmjCXpZcIzt0e3XFWVIDpY5/N7UNVww5BWM8Eq8oxg/gLaUexGh7VVAkBEFfPEkt8mIubwlA8phlKlTOgxhDNUV+aaM21InPrk5s30YJRHBS+PQE6kqtLAJr3zZOL+0HoBMiDsFMUzkpWVAkATPYXNxLfcLiTpR+gPNy83gn31fJDfp/74lP3l4qjghP0iYq8ZWKjHbJJC/9loXR9p7UT6HTxfcksaYD9NqDGBAkEArthqUn/ywKab8exX+a+8dYoHGDo7yhn6ezs3wW1ajLUYhoeUb9Y7I9W4A6s8OE6OpbL9ywnrly3CFM7dLbUejQ==";
		try {
			ArrayList<Spec> list = new ArrayList<>();

			Spec spec = new Spec("顏色");Spec spec1 = new Spec("尺寸");
			list.add(spec);list.add(spec1);
			Product product=new Product("1","iphone8",5688.0,list);
			Product product2=new Product("2","Galaxy",6188.0,list);
			ArrayList<Product> plist = new ArrayList<>();
			HashMap<String,Object> map=new HashMap<>();
			plist.add(product);
			plist.add(product2);
			map.put("productList", plist);
			String sign = RSAUtils.sign(plist, privateKeyStr, "RSA", "utf-8");
			map.put("sign", sign);
			byte[] byteArray = SerializeUtils.serialize(map);	
			byte[] encryptByPrivateKey = RSAUtils.encryptByPrivateKey(byteArray, privateKeyStr, "RSA");
			amqpTemplate.convertAndSend( "boys.love.girls",encryptByPrivateKey);	
			return "消息成功发送";
		} catch (Exception e) {
			return "参数或mq服务器有误";
		}
			
	}
}
