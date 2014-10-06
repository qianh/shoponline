package com.qh.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.qh.shop.dao.IAddressDao;
import com.qh.shop.dao.IUserDao;
import com.qh.shop.model.Address;
import com.qh.shop.model.ShopDi;
import com.qh.shop.model.User;
import com.qh.shop.util.RequestUtil;


public class AddressServlet extends BaseServlet {
	private static final long serialVersionUID = 4217817486536907220L;
	
	private IUserDao userDao;
	private IAddressDao addressDao;
	
	@ShopDi("addressDao")
	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}
	
	@ShopDi("userDao")
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	@Auth
	public String add(HttpServletRequest req,HttpServletResponse resp){
		User u = userDao.load(Integer.parseInt(req.getParameter("userId")));
		Address a =(Address)RequestUtil.setParam(Address.class, req);
		boolean isvalidate = RequestUtil.validate(Address.class, req);
		if(!isvalidate){
			req.setAttribute("user", u);
			return "address/addInput.jsp";
		}
		addressDao.add(a, u.getId());
	
		return redirpath(req.getContextPath()+"/user.do?method=show&id="+u.getId());
	}
	
	@Auth
	public String addInput(HttpServletRequest req,HttpServletResponse resp) {
		User u = userDao.load(Integer.parseInt(req.getParameter("userId")));
	    req.setAttribute("user", u);
		return "address/addInput.jsp";
	}
	
	@Auth
	public String delete(HttpServletRequest req,HttpServletResponse resp) {
		int addressId=Integer.parseInt(req.getParameter("id"));
		int userId = Integer.parseInt(req.getParameter("userId"));
		addressDao.delete(addressId);
		return redirpath(req.getContextPath()+"/user.do?method=show&id="+userId);
	}
	
	@Auth
	public String updateInput(HttpServletRequest req,HttpServletResponse resp) {
		int addressId = Integer.parseInt(req.getParameter("id"));
		Address address = addressDao.load(addressId);
	    req.setAttribute("address", address);
		return "address/updateInput.jsp";
	}
	
	@Auth
	public String update(HttpServletRequest req,HttpServletResponse resp) {
		int addressId = Integer.parseInt(req.getParameter("id"));
		Address address = addressDao.load(addressId);
		Address ta =(Address) RequestUtil.setParam(Address.class, req);
		address.setName(ta.getName());
		address.setPhone(ta.getPhone());
		address.setPostcode(ta.getPostcode());
		if(!RequestUtil.validate(Address.class,req)){
			req.setAttribute("address", address);
			return "address/updateInput.jsp";
		}
		addressDao.update(address);
		return redirpath(req.getContextPath()+"/user.do?method=show&id="+address.getUser().getId()); 
	}

}
