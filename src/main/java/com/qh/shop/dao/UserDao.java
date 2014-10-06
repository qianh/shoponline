package com.qh.shop.dao;

import java.util.HashMap;
import java.util.Map;
import com.qh.shop.model.Pager;
import com.qh.shop.model.ShopException;
import com.qh.shop.model.User;

public class UserDao extends BaseDao<User> implements IUserDao{

	@Override
	public void add(User user) {
		User tu=this.loadByUsername(user.getUsername());
		if(tu!=null) throw new ShopException("要添加的用户已经存在！");
		super.add(user);	
	}

	@Override
	public void update(User user) {
		super.update(user);
	}

	public void delete(int id){
		super.delete(User.class,id);
	}

	public User load(int id) {
	   return super.load(User.class, id);
	}
	public User loadByUsername(String username) {
		return super.loadBySqlId(User.class.getName()+".loadByUsername", username);
	}

	public Pager<User> find(String name) {
			Map<String,Object> params = new HashMap<String, Object>();
			if(name!=null&&!name.equals(""))
			    params.put("name", "%"+name+"%");
		   return super.find(User.class, params);
	}

	public User login(String username, String password) {
		User u=this.loadByUsername(username);
		if(u==null) throw new ShopException("用户名不存在！");
		if(!password.equals(u.getPassword())) throw new ShopException("用户密码错误！");
		return u;
	}

}
