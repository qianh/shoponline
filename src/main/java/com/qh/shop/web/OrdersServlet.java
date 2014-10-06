package com.qh.shop.web;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qh.shop.dao.IOrdersDao;
import com.qh.shop.dao.IProductDao;
import com.qh.shop.dao.IUserDao;
import com.qh.shop.model.Orders;
import com.qh.shop.model.Product;
import com.qh.shop.model.ShopCart;
import com.qh.shop.model.ShopDi;
import com.qh.shop.model.ShopException;
import com.qh.shop.model.SystemContext;
import com.qh.shop.model.User;

public class OrdersServlet extends BaseServlet {
	private IProductDao productDao;
	private IUserDao userDao;
	private IOrdersDao ordersDao;
	
	

	public IOrdersDao getOrdersDao() {
		return ordersDao;
	}
	@ShopDi
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	public IUserDao getUserDao() {
		return userDao;
	}
	@ShopDi
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	public IProductDao getProductDao() {
		return productDao;
	}
	@ShopDi
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	private static final long serialVersionUID = 1L;

	@Auth
	public String addToCart(HttpServletRequest req,HttpServletResponse resp) {
		try {
			ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
			if(shopCart==null) {
				shopCart = new ShopCart();
				req.getSession().setAttribute("shopCart", shopCart);
			} 
			Product p = productDao.load(Integer.parseInt(req.getParameter("id")));
			shopCart.add(p);
		} catch (ShopException e) {
			return this.handleException(e, req);
		}
		return redirpath("product.do?method=list");
	}
	
	@Auth
	public String showCart(HttpServletRequest req,HttpServletResponse resp) {
		User u = (User)req.getSession().getAttribute("loginUser");
		req.setAttribute("addresses", userDao.load(u.getId()).getAddress());
		return "orders/showCart.jsp";
	}
	
	
	
	@Auth
	public String clearProduct(HttpServletRequest req,HttpServletResponse resp) {
		int pid = Integer.parseInt(req.getParameter("pid"));
		ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
		if(shopCart!=null) {
			shopCart.clearProduct(pid);
		} 
		return redirpath("orders.do?method=showCart");
	}
	
	@Auth
	public String clearShopCart(HttpServletRequest req,HttpServletResponse resp) {
		ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
		if(shopCart!=null) {
			shopCart.clearShopCart();
		} 
		return redirpath("orders.do?method=showCart");
	}
	
	@Auth
	public String productAddNumberInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("pid", Integer.parseInt(req.getParameter("pid")));
		return "orders/productAddNumberInput.jsp";
	}
	
	@Auth
	public String productAddNumber(HttpServletRequest req,HttpServletResponse resp) {
		int pid = Integer.parseInt(req.getParameter("pid"));
		try {
			int number = Integer.parseInt(req.getParameter("number"));
			ShopCart shopCart = (ShopCart)req.getSession().getAttribute("shopCart");
			if(shopCart!=null) {
				shopCart.addProductNumber(pid,number);
			} 
		} catch (NumberFormatException e) {
			this.getErrors().put("number", "��������Ϊ����");
			req.setAttribute("pid", pid);
			return "orders/productAddNumberInput.jsp";
		} catch (ShopException e) {
			return this.handleException(e, req);
		}
		return redirpath("orders.do?method=showCart");
	}
	
	@Auth
	public String addOrders(HttpServletRequest req,HttpServletResponse resp) {
		int aid = Integer.parseInt(req.getParameter("address"));
		double price = Double.parseDouble(req.getParameter("price"));
		Orders o = new Orders();
		o.setBuyDate(new Date());
		o.setStatus(1);
		o.setPrice(price);
		User u = (User)req.getSession().getAttribute("loginUser");
		ordersDao.add(o,u , aid,
				((ShopCart)req.getSession().getAttribute("shopCart")).getProducts());
		return redirpath("user.do?method=show&id="+u.getId());
	}
	
	public String list(HttpServletRequest req,HttpServletResponse resp) {
		SystemContext.setOrder("desc");
		SystemContext.setSort("buy_date");
		int status = 0;
		try {
			status = Integer.parseInt(req.getParameter("status"));
		} catch (NumberFormatException e) {}
		req.setAttribute("orders", ordersDao.findByStatus(status));
		return "orders/list.jsp";
	}
	
	@Auth
	public String userList(HttpServletRequest req,HttpServletResponse resp) {
		SystemContext.setOrder("desc");
		SystemContext.setSort("buy_date");
		int status = 0;
		int userId = 0;
		try {
			userId = Integer.parseInt(req.getParameter("id"));
			status = Integer.parseInt(req.getParameter("status"));
		} catch (NumberFormatException e) {}
		req.setAttribute("orders", ordersDao.findByUser(userId, status));
		return "orders/userList.jsp";
	}
	
	@Auth
	public String delete(HttpServletRequest req,HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		ordersDao.delete(id);
		return redirpath("product.do?method=list");
	}
	
	@Auth
	public String show(HttpServletRequest req,HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		req.setAttribute("o", ordersDao.load(id));
		return "orders/show.jsp";
	}
	
	@Auth
	public String pay(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			ordersDao.updatePayStatus(id);
		} catch (ShopException e) {
			return handleException(e, req);
		}
		return redirpath("orders.do?method=userList&id="+((User)req.getSession().getAttribute("loginUser")).getId());
	}
	
	@Auth
	public String confirmProduct(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			ordersDao.updateConfirmStatus(id);
		} catch (ShopException e) {
			return handleException(e, req);
		}
		return redirpath("orders.do?method=userList&id="+((User)req.getSession().getAttribute("loginUser")).getId());
	}
	
	public String sendProduct(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			ordersDao.updateSendStatus(id);
		} catch (ShopException e) {
			return handleException(e, req);
		}
		return redirpath("orders.do?method=list");
	}
	
}
