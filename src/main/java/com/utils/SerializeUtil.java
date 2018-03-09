package com.utils;

import java.io.*;

public class SerializeUtil {

	 /**  
     * 序列化
     * @param obj  
     * @return  
     */  
    public static byte[] toByteArray (Object obj) {      
        byte[] bytes = null;      
        try {        
        	ByteArrayOutputStream bos = new ByteArrayOutputStream();      
            ObjectOutputStream oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray ();      
            oos.close();         
            bos.close();        
        } catch (IOException ex) {        
            ex.printStackTrace();   
        }      
        return bytes;    
    }   
       
    /**  
     *反序列化
     * @param bytes  
     * @return  
     */  
    @SuppressWarnings("unchecked")
	public static <T> T toObject(byte[] bytes) {      
        T t = null;      
        try {        
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);        
            ObjectInputStream ois = new ObjectInputStream (bis);        
            t = (T) ois.readObject();      
            ois.close();   
            bis.close();   
        } catch (IOException ex) {        
            ex.printStackTrace();   
        } catch (ClassNotFoundException ex) {        
            ex.printStackTrace();   
        }      
        return t;    
    }   
       
	
}
