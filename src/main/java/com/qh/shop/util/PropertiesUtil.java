package com.qh.shop.util;

import java.util.Properties;

public class PropertiesUtil {
    private static Properties daoProp;
    
    public static Properties getDaoPorp(){
    	try{
    		if(daoProp==null){
    		daoProp = new Properties();
    		daoProp.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("dao.properties"));
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return daoProp;
    }
}
