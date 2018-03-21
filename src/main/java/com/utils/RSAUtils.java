package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



public class RSAUtils {
	  
		private final static String ENCRYPT="ENCRYPT";
		private final static String DECRYPT="DECRYPT";
		public enum SignType {
		  RSA("SHA1WithRSA"), RSA2("SHA256WithRSA");
		  private String name;
		  private SignType(String name) {
			this.name = name;				
		  }
	    }


	/**
     * 生成密钥对 
     * @param keyLength 1024或2048
     * @return
     * @throws Exception 
     */
    public static final KeyPair genKeyPair(int keyLength)  {  
    
			KeyPairGenerator keyPairGenerator;
			try {
				keyPairGenerator = KeyPairGenerator.getInstance("RSA");
				keyPairGenerator.initialize(keyLength);  
				 return keyPairGenerator.generateKeyPair();  
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return null;
		
    }  
   
    /**
     * 将base64编码后的公钥字符串转成PublicKey实例  
     * @param publicKey 公钥(BASE64编码)
     * @return
     */
    public static final PublicKey getPublicKey(String publicKey)  {  

			try {
				byte[ ] keyBytes=Base64.getDecoder().decode(publicKey);  
				X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);  
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				return   keyFactory.generatePublic(keySpec);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
			
			return null;   
    }  
      

    /**
     * 将base64编码后的私钥字符串转成PrivateKey实例  
     * @param privateKey 私钥(BASE64编码)
     * @return
     */
    public static final PrivateKey getPrivateKey(String privateKey)throws Exception {  
		
			byte[ ] keyBytes=Base64.getDecoder().decode(privateKey);  
			PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);  
			 KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
    }
    /**
     * 根据加密方式返判断返回明文以及密钥最大限制长度
     * @param type 加密或者解密
     * @param signType 加密方式RSA或RSA2
     * @return
     * @throws NoSuchAlgorithmException 
     */
	private static final int max_block(String type,String signType) throws Exception {
		
		if (SignType.RSA.toString().equals(signType)) {
			if (ENCRYPT.equals(type)) {
				return 117;
			} else if(DECRYPT.equals(type)){
				return 128;
			}
		} else if(SignType.RSA2.toString().equals(signType)) {
			if (ENCRYPT.equals(type)) {
				return 245;
			} else if(DECRYPT.equals(type)){
				return 256;
			}
		}
		
    	throw new  Exception("("+signType+") 是无效的加密方式 ,仅支持(RSA)和(RSA2)");  	
	}
    	/**
    	 * @param signType 加密方式
    	 * @return
    	 * @throws NoSuchAlgorithmException 
    	 */
    	private static String signType(String signType) throws Exception {
    		if (SignType.RSA.toString().equals(signType)) {
				return SignType.RSA.name;
			} else if(SignType.RSA2.toString().equals(signType)){
				return SignType.RSA2.name;
			}
    		
    		throw new  Exception("("+signType+") 是无效的加密方式 ,仅支持(RSA)和(RSA2)");  
    		
		}
    /**
     * 公钥加密  (没有长度限制)
     * @param data 源数据
     * @param publicKeyStr 公钥(BASE64编码)
     * @param signType 加密方式RSA或RSA2 
     * @return
     * @throws Exception 
     */
    public static final String encryptByPublicKey(String data, String publicKeyStr,String signType) throws Exception {  
		
						
				
					PublicKey	publicKey = getPublicKey(publicKeyStr);
					byte[] content=data.getBytes();
					Cipher cipher = Cipher.getInstance("RSA");
					cipher.init(Cipher.ENCRYPT_MODE, publicKey); 
					int block=max_block(ENCRYPT,signType);
					int inputLen = content.length;
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					byte[] caches ;
					int offSet = 0;
					int i = 0;
					// 对数据分段加密
					while (inputLen - offSet > 0) {
						if (inputLen - offSet > block) {		            	
							caches = cipher.doFinal(content, offSet, block);
						} else {
							caches = cipher.doFinal(content, offSet, inputLen - offSet);
						}
						out.write(caches, 0, caches.length);
						i++;
						offSet = i * block;
					}
					byte[] encryptedData = out.toByteArray();
					out.close();
					return Base64.getEncoder().encodeToString(encryptedData);
				
						
    }  
    			public static String log(Exception e) {
    				StringBuffer sb=new StringBuffer();
    				sb.append("["+e.getMessage()+"]");
					StackTraceElement[] stackTrace = e.getStackTrace();
					for (int i = 0; i < stackTrace.length; i++) {
						sb.append("\n"+stackTrace[i]);
					}
					return sb.toString();

				}
    /**
     * 私钥加密  (没有长度限制)
     * @param data 源数据
     * @param privateKeyStr 私钥(BASE64编码) 
     * @param signType 加密方式RSA或RSA2
     * @return
     * @throws Exception 
     */
    public static final String encryptByPrivateKey(String data, String privateKeyStr,String signType) throws Exception  {  
		

			try {
				PrivateKey privateKey=getPrivateKey(privateKeyStr);    	
				byte[] content=data.getBytes();
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.ENCRYPT_MODE, privateKey); 
				 int inputLen = content.length;
			      ByteArrayOutputStream out = new ByteArrayOutputStream();
			      int block=max_block(ENCRYPT,signType);
			      byte[] caches ;
			        int offSet = 0;
			        int i = 0;
			        // 对数据分段加密
			        while (inputLen - offSet > 0) {
			            if (inputLen - offSet >block) {
			            	caches = cipher.doFinal(content, offSet, block);
			            } else {
			            	caches = cipher.doFinal(content, offSet, inputLen - offSet);
			            }
			            out.write(caches, 0, caches.length);
			            i++;
			            offSet = i * block;
			        }
			        byte[] encryptedData = out.toByteArray();
			        out.close();
				  return Base64.getEncoder().encodeToString(encryptedData);
			} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new Exception("Encryption failure ");
	
    }  
    /**
     * 私钥加密  (没有长度限制)
     * @param content 源数据
     * @param privateKeyStr 私钥(BASE64编码) 
     * @param signType 加密方式RSA或RSA2
     * @return
     * @throws Exception 
     */
    public static final byte[] encryptByPrivateKey(byte[] content, String privateKeyStr,String signType) throws Exception  {  
		
		try {
			PrivateKey privateKey=getPrivateKey(privateKeyStr);    	
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey); 
			 int inputLen = content.length;
		      ByteArrayOutputStream out = new ByteArrayOutputStream();
		      int block=max_block(ENCRYPT,signType);
		      byte[] caches ;
		        int offSet = 0;
		        int i = 0;
		        // 对数据分段加密
		        while (inputLen - offSet > 0) {
		            if (inputLen - offSet >block) {
		            	caches = cipher.doFinal(content, offSet, block);
		            } else {
		            	caches = cipher.doFinal(content, offSet, inputLen - offSet);
		            }
		            out.write(caches, 0, caches.length);
		            i++;
		            offSet = i * block;
		        }
		        byte[] encryptedData = out.toByteArray();
		        out.close();
			  return encryptedData;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new Exception("Encryption failure ");

}   
    /**
     * 私钥解密  (没有长度限制)
     * @param content 加密数据 
     * @param privateKeyStr 私钥(BASE64编码) 
     * @param signType 加密方式RSA或RSA2
     * @return
     * @throws Exception 
     */
    public static final String decryptByPrivateKey(String content, String privateKeyStr,String signType) throws Exception  {  
     
			try {
				PrivateKey privateKey=getPrivateKey(privateKeyStr) ;
				byte[] contentBytes=Base64.getDecoder().decode(content);
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				  int inputLen = contentBytes.length;
				  int block=max_block(DECRYPT,signType);
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        int offSet = 0;
			        byte[] caches;
			        int i = 0;
			        // 对数据分段解密
			        while (inputLen - offSet > 0) {
			            if (inputLen - offSet > block) {
			                caches = cipher.doFinal(contentBytes, offSet, block);
			            } else {
			                caches = cipher.doFinal(contentBytes, offSet, inputLen -offSet);
			            }
			            out.write(caches, 0, caches.length);
			            i++;
			            offSet = i * block;
			        }
			        byte[]  decryptedData= out.toByteArray();
			        out.close(); 
				return new String( decryptedData);  
			} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		throw new Exception("decryption failure");
    } 
    
    
    /**
     * 公钥解密  (没有长度限制)
     * @param content 加密数据 
     * @param publicKeyStr 公钥(BASE64编码)  
     * @param signType 加密方式RSA或RSA2
     * @return
     * @throws Exception 
     */
    public static final byte[]  decryptByPublicKey(String content, String publicKeyStr,String signType) throws Exception  {  
     
			try {
				PublicKey privateKey=getPublicKey(publicKeyStr) ;
				byte[] contentBytes=Base64.getDecoder().decode(content);
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, privateKey);  
				  int inputLen = contentBytes.length;
				  int block=max_block(DECRYPT,signType);
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        int offSet = 0;
			        byte[] caches;
			        int i = 0;
			        // 对数据分段解密
			        while (inputLen - offSet > 0) {
			            if (inputLen - offSet > block) {
			                caches = cipher.doFinal(contentBytes, offSet, block);
			            } else {
			                caches = cipher.doFinal(contentBytes, offSet, inputLen -offSet);
			            }
			            out.write(caches, 0, caches.length);
			            i++;
			            offSet = i * block;
			        }
			        byte[]  decryptedData= out.toByteArray();
			        out.close(); 
				return decryptedData;    
			} catch ( NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new Exception("decryption failure");
    } 
    /**
     * 公钥解密  (没有长度限制)
     * @param content 加密数据 
     * @param publicKeyStr 公钥(BASE64编码)  
     * @param signType 加密方式RSA或RSA2
     * @return
     * @throws Exception 
     */
    public static final byte[]  decryptByPublicKey(byte[] content, String publicKeyStr,String signType) throws Exception  {  
     
			try {
				PublicKey privateKey=getPublicKey(publicKeyStr) ;
				//byte[] contentBytes=Base64.getDecoder().decode(content);
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, privateKey);  
				  int inputLen = content.length;
				  int block=max_block(DECRYPT,signType);
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        int offSet = 0;
			        byte[] caches;
			        int i = 0;
			        // 对数据分段解密
			        while (inputLen - offSet > 0) {
			            if (inputLen - offSet > block) {
			                caches = cipher.doFinal(content, offSet, block);
			            } else {
			                caches = cipher.doFinal(content, offSet, inputLen -offSet);
			            }
			            out.write(caches, 0, caches.length);
			            i++;
			            offSet = i * block;
			        }
			        byte[]  decryptedData= out.toByteArray();
			        out.close(); 
				return decryptedData;    
			} catch ( NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new Exception("decryption failure");
    } 
   /** 
     * 用私钥对信息生成数字签名 
     * @param content 加密数据
     * @param privateKeyStr 私钥(BASE64编码) 
     * @param signType 加密方式RSA或RSA2
     * @param charset 字符集编码格式
     * 
     * @return String 数字签名 
     * 
     */ 
    public static final String sign(Object content, String privateKeyStr,String signType,String charset) throws Exception {

            PrivateKey privateKey = getPrivateKey(privateKeyStr);
            Signature signature =Signature.getInstance(signType(signType));
            signature.initSign(privateKey);
            signature.update(content.toString().getBytes(charset));
            return Base64.getEncoder().encodeToString(signature.sign());
    }  

    /** 
     * 校验数字签名 
     * @param content 加密数据 
     * @param publicKeyStr 公钥(BASE64编码) 
     * @param sign 数字签名 
     * @param signType 加密方式 RSA或RSA2
     * @param charset 字符集编码格式
     * @return 
     * 
     */  
    public static final boolean verify(Object content, String publicKeyStr, String sign,String signType,String charset)throws Exception  {
      	       	
        	 PublicKey publicKey=getPublicKey(publicKeyStr); 
            Signature signature = Signature.getInstance(signType(signType));           
            signature.initVerify(publicKey);
            signature.update(content.toString().getBytes(charset));
            return signature.verify(Base64.getDecoder().decode(sign));

    }

}
