package com.qh.shop.dao;

import org.junit.Test;
import static org.junit.Assert.*;
import com.qh.shop.dao.IUserDao;
import com.qh.shop.model.Address;
import com.qh.shop.model.Pager;
import com.qh.shop.model.ShopDi;
import com.qh.shop.model.SystemContext;
import com.qh.shop.model.User;

public class TestUserDao extends BaseTest{
	private IUserDao userdao;
	
	public IUserDao getUserdao() {
		return userdao;
	}
    @ShopDi("userDao")
	public void setUserdao(IUserDao userdao) {
		this.userdao = userdao;
	}

	@Test
    public void testAdd(){
    	User u=new User();
    	u.setNickname("ΜΖΙ®");
    	u.setPassword("1234");
    	u.setType(0);
    	u.setUsername("tong");
    	userdao.add(u);
    }
	
	@Test
	public void testUpdate(){
		User u=new User();
		u.setId(2);
		u.setNickname("Αυ±Έ");
		u.setPassword("654321");
		u.setType(0);
		u.setUsername("wujizhang");
		userdao.update(u);
	}
	
	@Test
	public void testDelete(){
		assertNotNull(userdao.load(1));
		userdao.delete(1);
		assertNull(userdao.load(1));
	}
	
	@Test
	public void testLoad(){
		User u = userdao.load(1);
		for(Address a : u.getAddress()){
		System.out.println(a);	}
		User un=userdao.loadByUsername("Αυ±Έ");
		for(Address a: un.getAddress()){
		System.out.println(a);}
	}
	
	@Test
	public void testLogin(){;
		User u = userdao.login("wujizhang", "654321");
		assertTrue(u!=null);
	}
	
	@Test
	public void testFind(){
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		SystemContext.setOrder("desc");
		SystemContext.setSort("username");
		Pager<User> ps=userdao.find("");
		System.out.println(ps.getTotalRecord());
		for(User u:ps.getDatas()){
			System.out.println(u);
		}
	}

}
