package com.qh.shop.dao;

import java.util.HashMap;
import java.util.Map;
import com.qh.shop.model.Category;
import com.qh.shop.model.Pager;
import com.qh.shop.model.Product;
import com.qh.shop.model.ShopDi;
import com.qh.shop.model.ShopException;

public class ProductDao extends BaseDao<Product> implements IProductDao {
	private ICategoryDao categoryDao;
	
	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}
	@ShopDi("categoryDao")
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public void add(int cid,Product product) {
		Category c = categoryDao.load(cid);
		if(c==null) throw new ShopException("要添加的商品的类别不存在!");
		product.setCategory(c);
		super.add(product);
	}

	public void update(int cid,Product product) {
	
		super.update(product);
	}

	public void delete(int id) {	
		super.delete(Product.class, id);
	}

	public Product load(int id) {
		return super.load(Product.class, id);
	}

	public Pager<Product> find(int cid, String name,int status) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(cid>0) {
			params.put("cid", cid);
		}
		if(name!=null&&!"".equals(name.trim())) {
			params.put("name", "%"+name+"%");
		}
		if(status==1||status==-1) {
			params.put("status", status);
		}
		return super.find(Product.class, params);
	}

	public void addStock(int id, int num) {
		Product p = this.load(id);
		p.setStock(p.getStock()+num);
		this.update(p);
	}

	public void decreaseStock(int id, int num) {
		Product p = this.load(id);
		p.setStock(p.getStock()-num);
		this.update(p);
	}
	
	public void changeStatus(int id) {
		Product p = this.load(id);
		if(p.getStatus()==-1) {
			p.setStatus(1);
		} else {
			p.setStatus(-1);
		}
		this.update(p);
	}

}
