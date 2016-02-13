package com.iveely.framework.text;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author liufanping@iveely.com
 * @date 2015-1-25 16:51:33
 */
public class JsonUtil {

	public static String objectToJson(Object object) {
		StringBuilder json = new StringBuilder();
		if (object == null) {
			json.append("\"\"");
		} else if (object instanceof String || object instanceof Integer || object instanceof Long
				|| object instanceof Double || object instanceof Enum) {
			json.append("\"").append(object.toString()).append("\"");
		} else if (object instanceof String[]) {
			String[] arr = (String[]) object;
			json.append("[");
			for (int i = 0; i < arr.length - 1; i++) {
				json.append("\"").append(arr[i].replace("\"", "\\\"")).append("\",");
			}
			json.append("\"").append(arr[arr.length - 1].replace("\"", "\\\"")).append("\"");
			json.append("]");
		} else if (object instanceof Integer[]) {
			Integer[] arr = (Integer[]) object;
			json.append("[");
			for (int i = 0; i < arr.length - 1; i++) {
				json.append("\"").append(arr[i]).append("\",");
			}
			json.append("\"").append(arr[arr.length - 1]).append("\"");
			json.append("]");
		} else if (object instanceof boolean[]) {
			boolean[] arr = (boolean[]) object;
			json.append("[");
			if (arr.length > 0) {
				for (int i = 0; i < arr.length - 1; i++) {
					json.append("\"").append(arr[i]).append("\",");
				}
				json.append("\"").append(arr[arr.length - 1]).append("\"");
			}
			json.append("]");
		} else if (object instanceof Object[]) {
			Object[] arr = (Object[]) object;
			json.append("[");
			if (arr.length > 0) {
				for (int i = 0; i < arr.length - 1; i++) {
					json.append("\"").append(arr[i].toString().replace("\"", "\\\"")).append("\",");
				}
				json.append("\"").append(arr[arr.length - 1].toString().replace("\"", "\\\"")).append("\"");
			}
			json.append("]");
		} else if (object instanceof ArrayList) {
			json.append("[");
			List<Object[]> list = (List<Object[]>) object;
			if (list.size() > 0) {
				for (int i = 0; i < list.size() - 1; i++) {
					json.append(objectToJson(list.get(i))).append(",");
				}
				json.append(objectToJson(list.get(list.size() - 1)));
			}
			json.append("]");
		} else if (object instanceof HashMap) {
			HashMap<Object, Object> map = (HashMap<Object, Object>) object;
			int k = map.size();
			int index = 0;
			Iterator iter = map.keySet().iterator();
			json.append("{");
			while (iter.hasNext()) {
				Object key = iter.next();
				Object val = map.get(key);
				json.append("\"").append(key).append("\":").append("\"").append(val).append("\"");
				if (index != k - 1) {
					json.append(",");
				}
				index++;
			}
			json.append("}");

		} else if (object instanceof IdentityHashMap) {
			IdentityHashMap<Object, Object> map = (IdentityHashMap<Object, Object>) object;
			int k = map.size();
			int index = 0;
			Iterator iter = map.keySet().iterator();
			json.append("{");
			while (iter.hasNext()) {
				Object key = iter.next();
				Object val = map.get(key);
				json.append("\"").append(key).append("\":").append("\"").append(val).append("\"");
				if (index != k - 1) {
					json.append(",");
				}
				index++;
			}
			json.append("}");

		} else {
			json.append(beanToJson(object));
		}
		return json.toString();
	}

	public static String beanToJson(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = objectToJson(props[i].getName());
					Object reVal = props[i].getReadMethod().invoke(bean);
					String value = objectToJson(reVal);
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	public static String listToJson(List<?> list) {
		StringBuilder json = new StringBuilder();

		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}
}
