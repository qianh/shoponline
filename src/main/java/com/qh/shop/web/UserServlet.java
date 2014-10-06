package com.qh.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qh.shop.dao.IUserDao;
import com.qh.shop.model.Pager;
import com.qh.shop.model.ShopDi;
import com.qh.shop.model.ShopException;
import com.qh.shop.model.User;
import com.qh.shop.util.RequestUtil;

public class UserServlet extends BaseServlet {
    
	private static final long serialVersionUID = 4217817486536907220L;
	private IUserDao userDao;
	
	public IUserDao getUserDao() {
		return userDao;
	}
	@ShopDi("userDao")
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public String list(HttpServletRequest req,HttpServletResponse resp){
		 Pager<User> users = userDao.find("");
		 req.setAttribute("users", users);
		 return "user/list.jsp";
	}
	
	@Auth("any")
	public String addInput(HttpServletRequest req,HttpServletResponse resp){
		 return "user/addInput.jsp";
	}
	
	public String delete(HttpServletRequest req,HttpServletResponse resp){
		int id = Integer.parseInt(req.getParameter("id"));
		userDao.delete(id);
		return redirPath+"user.do?method=list";
	}

	
	public String changeType(HttpServletRequest req,HttpServletResponse resp){
		int id = Integer.parseInt(req.getParameter("id"));
		User u = userDao.load(id);
		if(u.getType()==0){
			u.setType(1);
		}else{
			u.setType(0);
		}
		userDao.update(u);
		return redirPath+"user.do?method=list";
	}
	@Auth("any")
	public String add(HttpServletRequest req,HttpServletResponse resp){
		User u = (User)RequestUtil.setParam(User.class,req);
		boolean isValidate = RequestUtil.validate(User.class, req);
		if(!isValidate){
			return "user/addInput.jsp";
		}
		
		try {
			userDao.add(u);
		} catch (ShopException e) {
			req.setAttribute("errorMsg", e.getMessage());
			return "inc/error.jsp";
		}
		return redirpath("user.do?method=list");
	}
	
	@Auth("any")
	public String loginInput(HttpServletRequest req,HttpServletResponse resp) {
		return "user/loginInput.jsp";
	}
	
	@Auth("any")
	public String login(HttpServletRequest req,HttpServletResponse resp) {
		try {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			User u = userDao.login(username, password);
			req.getSession().setAttribute("loginUser", u);
		} catch (ShopException e) {
			req.setAttribute("errorMsg",e.getMessage());
			return "inc/error.jsp";
		}
		return redirpath("product.do?method=list");
	}
	
	@Auth("any")
	public String loginOut(HttpServletRequest req,HttpServletResponse resp) {
		req.getSession().invalidate();
		return redirpath("user.do?method=list");
	}
	
	@Auth
	public String updateSelfInput(HttpServletRequest req,HttpServletResponse resp){
	    req.setAttribute("user", req.getSession().getAttribute("loginUser"));
		return "user/updateSelfInput.jsp";
	}
	
    @Auth
	public String updateSelf(HttpServletRequest req,HttpServletResponse resp){
    	User tu = (User)RequestUtil.setParam(User.class, req);
    	boolean isValidate = RequestUtil.validate(User.class, req);
		User user =(User) req.getSession().getAttribute("loginUser");
		user.setPassword(tu.getPassword());
		user.setNickname(tu.getNickname());
		if(!isValidate){
			req.setAttribute("user", user);
			return "user/updateSelfInput.jsp";
		}
		userDao.update(user);
		return redirpath("product.do?method=list");
	}
    
    @Auth
	public String show(HttpServletRequest req,HttpServletResponse resp) {
		User user = userDao.load(Integer.parseInt(req.getParameter("id")));
		req.setAttribute("user", user);
		return "user/show.jsp";
	}
}
