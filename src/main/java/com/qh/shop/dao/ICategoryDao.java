package com.qh.shop.dao;

import java.util.List;

import com.qh.shop.model.Category;

public interface ICategoryDao {
   public void add(Category category);
   public void update(Category category);
   public void delete(int id);
   public List<Category> list();
   public List<Category> list(String name);
   public Category load(int id);
}
