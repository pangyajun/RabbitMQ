package com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JesonUtils {

	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(Object object,Class<?> clazz) {
		JSONObject jsonObject=JSONObject.fromObject(object);
		T t = (T) JSONObject.toBean(jsonObject, clazz);	
		return  t;
	}
	
	@SuppressWarnings("unchecked")
	public static    <T> T  jsonToList(Object str,Class<?> clazz){
		JSONArray jsonArray=JSONArray.fromObject(str);
		 List<T> array = (List<T>) JSONArray.toCollection(jsonArray, clazz);
		return (T) array ;

	}
	
	@SuppressWarnings("unchecked")
	public static Map<Object,Object>  jsonToMap(String str) {
		Map<Object,Object> map= JSONObject.fromObject( JSONObject.fromObject(str));
			return map;
		}
     	
	public static String ObjectToJson(Object object) {		
		JSONObject fromObject = JSONObject.fromObject(object);
		return fromObject.toString();
	}
	public static String MapToJson(Object object) {
		JSONObject fromObject = JSONObject.fromObject(object);
		return fromObject.toString();

	}
	
	public static String ListToJson(Object object) {
		 JSONArray fromObject = JSONArray.fromObject(object);
		return fromObject.toString();

	}
/*	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String str="{\"price\":[{\"min_number\":1,\"max_number\":2,\"sell_price\":390,\"bonus_percent\":400,\"origin_price\":78},{\"min_number\":3,\"max_number\":19,\"sell_price\":378,\"bonus_percent\":400,\"origin_price\":75.6},{\"min_number\":20,\"max_number\":0,\"sell_price\":360,\"bonus_percent\":400,\"origin_price\":72}],\"moq\":1}";
	Map<Object, Object> jsonToMap = jsonToMap(str);
	Object object = jsonToMap.get("price");
	int moq =  (int) jsonToMap.get("moq");
	List<Price> jsonToList = jsonToList(object,Price.class);
	//System.out.println( jsonToList);
	List<Price> list=new ArrayList<>();
	for (Price price : jsonToList) {
		price.setBonus_percent("5");
		list.add(price);
	}
	//System.out.println(ListToJson(list));
	HashMap<Object, Object> hashMap = new HashMap<>();
	hashMap.put("price", list);
	hashMap.put("moq",moq);
	String mapToJson = MapToJson(hashMap);
	System.out.println(mapToJson);

	}*/
}
