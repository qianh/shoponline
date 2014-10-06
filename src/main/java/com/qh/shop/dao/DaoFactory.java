package com.qh.shop.dao;

public class DaoFactory {
     public static IUserDao getUserDao(){
    	 return new UserDao();
     }
     public static IAddressDao getIAddressDao(){
    	 return new AddressDao();
     }
}
