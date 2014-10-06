package com.qh.shop.dao;

import java.util.List;

import org.junit.Test;

import com.qh.shop.dao.IAddressDao;
import com.qh.shop.dao.IUserDao;
import com.qh.shop.model.Address;
import com.qh.shop.model.ShopDi;

public class TestAddressDao extends BaseTest{
	private IAddressDao addressDao;
	private IUserDao userDao;

	public IAddressDao getAddressDao() {
		return addressDao;
	}
    @ShopDi("addressDao")
	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
    @ShopDi("userDao")
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	

	@Test
	public void testAdd() {
		Address a = new Address();
		a.setName("浙江省宁波市");
		a.setPhone("057488432950");
		a.setPostcode("315100");
		addressDao.add(a, 3);
	}

	@Test
	public void testdelete() {
		addressDao.delete(5);
	}

	@Test
	public void testUpdate() {
		Address a = new Address();
		a.setId(2);
		a.setName("浙江省宁波市海曙区");
		a.setPhone("88123456");
		a.setPostcode("310000");
		a.setUser(userDao.load(1));
		addressDao.update(a);
	}

	@Test
	public void testLoad() {
		Address ad = addressDao.load(2);
		System.out.println(ad.getName() + "," + ad.getUser());
	}

	@Test
	public void testList() {
		List<Address> list = addressDao.list(2);
		for (Address a : list) {
			System.out.println(a.getName() + "," + a.getUser());
		}
	}
}
