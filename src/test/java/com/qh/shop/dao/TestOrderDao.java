package com.qh.shop.dao;



import org.junit.Test;

import com.qh.shop.dao.IOrdersDao;
import com.qh.shop.model.CartProduct;
import com.qh.shop.model.Orders;
import com.qh.shop.model.ShopDi;

public class TestOrderDao extends BaseTest {
	private IOrdersDao ordersDao;

	public IOrdersDao getOrdersDao() {
		return ordersDao;
	}
	@ShopDi
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	
	@Test
	public void testLoad() {
		Orders o = ordersDao.load(7);
		System.out.println(o.getPrice()+","+o.getStatus()+","+o.getAddress().getName()+","+o.getUser().getNickname());
		for(CartProduct cp:o.getProducts()) {
			System.out.println(cp.getProduct().getName()+","+cp.getNumber());
		}
	}
}
