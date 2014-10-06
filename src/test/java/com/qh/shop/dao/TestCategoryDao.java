package com.qh.shop.dao;

import java.util.List;

import org.junit.Test;

import com.qh.shop.dao.ICategoryDao;
import com.qh.shop.model.Category;
import com.qh.shop.model.ShopDi;

public class TestCategoryDao extends BaseTest {
    private ICategoryDao categoryDao;
 
    @ShopDi("categoryDao")
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
    @Test
    public void testAdd(){
    	Category category = new Category();
    	category.setName("夏季服装");
    	categoryDao.add(category);
    	 category = new Category();
    	category.setName("秋季服装");
    	categoryDao.add(category);
    	 category = new Category();
    	category.setName("手机及配件");
    	categoryDao.add(category);
    	 category = new Category();
    	category.setName("家电");
    	categoryDao.add(category);
    }
    @Test
    public void testLoad(){
    	Category category = categoryDao.load(1);
    	System.out.println(category.getName());
    }
    
    @Test
    public void testDelete(){
    	categoryDao.delete(1);
    }
    
    @Test
    public void testUpdate(){
    	Category c = categoryDao.load(2);
    	c.setName("春季服装");
    	categoryDao.update(c);
    }
    @Test
    public void testList(){
    	List<Category> list = categoryDao.list();
    	for(Category c :list){
    		System.out.println(c.getId()+","+c.getName());
    	}
    }
    
    @Test
    public void testListCon(){
    	List<Category> list = categoryDao.list("装");
    	for(Category c :list){
    		System.out.println(c.getId()+","+c.getName());
    	}
    }
    
    
}
