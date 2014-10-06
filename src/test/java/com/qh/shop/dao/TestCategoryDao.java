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
    	category.setName("�ļ���װ");
    	categoryDao.add(category);
    	 category = new Category();
    	category.setName("�＾��װ");
    	categoryDao.add(category);
    	 category = new Category();
    	category.setName("�ֻ������");
    	categoryDao.add(category);
    	 category = new Category();
    	category.setName("�ҵ�");
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
    	c.setName("������װ");
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
    	List<Category> list = categoryDao.list("װ");
    	for(Category c :list){
    		System.out.println(c.getId()+","+c.getName());
    	}
    }
    
    
}
