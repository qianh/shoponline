package com.qh.shop.dao;


import com.qh.shop.model.Pager;
import com.qh.shop.model.User;

public interface IUserDao {
    public void add(User user);
    public void update(User user);
    public void delete(int id);
    public User load(int id);
    public User loadByUsername(String username);
    public Pager<User> find(String username);
    public User login(String username,String password);
}
