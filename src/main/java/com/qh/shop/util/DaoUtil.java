package com.qh.shop.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import com.qh.shop.dao.IFactoryDao;
import com.qh.shop.model.ShopDi;

public class DaoUtil {
	
	public static void diDao(Object obj){
		
			try {
				Method[] ms = obj.getClass().getDeclaredMethods();
				for(Method m : ms){
					if(m.isAnnotationPresent(ShopDi.class)){
						ShopDi sp = m.getAnnotation(ShopDi.class);
						String mn = sp.value();
						if(m==null||"".equals(mn.trim())){
						    mn = m.getName().substring(3);
						    mn = mn.substring(0,1).toLowerCase()+mn.substring(1);
						}
						Object o = DaoUtil.createDaoFactory().getDao(mn);
						m.invoke(obj, o);
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static IFactoryDao createDaoFactory(){
    	IFactoryDao factory = null;
    	try {
    		Properties pro = PropertiesUtil.getDaoPorp();
			String fs = pro.getProperty("factory");
			Class clz = Class.forName(fs);
			String mm = "getInstance";
			Method mtd = clz.getMethod(mm);	
			factory=(IFactoryDao)mtd.invoke(clz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
    	    return factory;
    	
    }
}
