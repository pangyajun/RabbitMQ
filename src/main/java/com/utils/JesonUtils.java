package com.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Mr.pang
 *
 * @date 2018年3月6日 下午1:56:13
 */
public class JesonUtils {

	/**
	 * JSON转对象
	 * 
	 * @param str
	 *            字符串格式json
	 * @param clazz
	 *            类对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(String str, Class<T> clazz) {
		JSONObject jsonObject = JSONObject.fromObject(str);
		T t = (T) JSONObject.toBean(jsonObject, clazz);
		return t;
	}

	/**
	 * 把json格式数据转化成对象 属性为集合的属性名必须是(list+class名)
	 * 
	 * @param str
	 * @param clazz
	 *            主类
	 * @param classes
	 *            属性为集合的所有类数组
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(String str, Class<T> clazz, Class<T>... classes) {
		JSONObject jsonObject = JSONObject.fromObject(str);
		T t = (T) JSONObject.toBean(jsonObject, clazz);
		Method[] methods = t.getClass().getMethods();
		List<Method> listMethod = new ArrayList<>();
		for (Method method : methods) {
			if (method.getName().startsWith("List", 3))
				listMethod.add(method);
		}
		if (listMethod.size() == 0)
			return t;
		try {
			for (Method method : listMethod) {
				for (Class<T> class1 : classes) {
					if (method.getName().equals("getList" + class1.getSimpleName())) {
						Object invoke;
						invoke = method.invoke(t);
						List<T> list = new ArrayList<>();
						for (Object object : (List<T>) invoke) {
							T bean = (T) JSONObject.toBean(JSONObject.fromObject(object), class1);

							T bean1 = jsonToObject(ObjectToJson(object), (Class<T>) bean.getClass(), classes);
							list.add(bean1);
						}
						for (Method method1 : listMethod) {
							if (method1.getName().equals("setList" + class1.getSimpleName()))
								method1.invoke(t, list);
						}
					}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block

		}

		return t;
	}

	/**
	 * JSON转map
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<Object, Object> jsonToMap(String str) {
		Map<Object, Object> map = JSONObject.fromObject(JSONObject.fromObject(str));
		return map;
	}

	/**
	 * JSON转List
	 * 
	 * @param str
	 * @param clazz
	 *            类对象
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToList(String str, Class<T> clazz) {
		JSONArray jsonArray = JSONArray.fromObject(str);
		List<T> array = (List<T>) JSONArray.toCollection(jsonArray, clazz);
		return (T) array;
	}

	/**
	 * JSON转数组
	 * 
	 * @param str
	 * @param clazz
	 *            类对象
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] JsonToArray(String str, Class<T> clazz) {
		JSONArray jsonArray = JSONArray.fromObject(str);
		T[] array = (T[]) JSONArray.toArray(jsonArray, clazz);
		return (T[]) array;

	}

	/**
	 * @param object
	 * @return
	 */
	public static String ObjectToJson(Object object) {

		if (object instanceof List || object instanceof Map || object instanceof Set || object.getClass().isArray()) {
			return JSONArray.fromObject(object).toString();
		} else {
			return JSONObject.fromObject(object).toString();
		}

	}

	
}
