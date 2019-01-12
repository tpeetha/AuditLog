/**
 * 
 */
package com.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author tpeetha
 *
 */
public class TestObject {

	public static void main(String[] args) {
		
		Student student = new Student();
		boolean isValid = isValidObject(student);
		
		System.out.println(isValid);
		
	}
	
	private static boolean isValidObject(Object object) {
		
		System.out.println("isValidObject------------");
		
		Class<? extends Object> classOfObject = object.getClass();
		Field[] fields = classOfObject.getDeclaredFields();
		
		for(int i=0;i<fields.length;i++) {
			fields[i].setAccessible(true);
			
			try {
				Object field = fields[i].get(object);

				if(field == null || field != null && "".equals(field.toString().trim())) {
					return false;
				}

				if(field instanceof Collection<?>) {
					if(!isValidCollection(field)) {
						return false;
					}
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public static <T> boolean isValidCollection(Object field) {
		System.out.println("isValidCollection ----");
		Iterator<T> iterator = ((Collection<T>) field).iterator();
		while(iterator.hasNext()) {
			Object itrObject = iterator.next();
			if(itrObject == null || itrObject != null && "".equals(itrObject.toString().trim())) {
				return false;
			}
			
			if(itrObject instanceof Collection<?>) {
				isValidObject(itrObject);
			}
		}
		return true;
	}
}


class Student {
	List<String> str1 = new ArrayList(Arrays.asList("ABC"));
	int sno;
	String sname = "test";
	 
}