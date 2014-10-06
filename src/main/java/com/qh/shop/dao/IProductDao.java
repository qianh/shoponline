package com.qh.shop.dao;

import com.qh.shop.model.Pager;
import com.qh.shop.model.Product;

public interface IProductDao {
	public void add(int cid,Product product);
	public void update(int cid,Product product);
	public void delete(int id);
	public Product load(int id);
	
	public Pager<Product> find(int cid,String name,int status);

	public void addStock(int id,int num);
	
	public void decreaseStock(int id,int num);
	
	public void changeStatus(int id);
}
