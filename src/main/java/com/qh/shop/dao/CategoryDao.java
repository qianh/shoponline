package com.qh.shop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qh.shop.model.Category;

public class CategoryDao extends BaseDao<Category> implements ICategoryDao {

	@Override
	public void add(Category category) {
        super.add(category);
	}

	@Override
	public void update(Category category) {
		super.update(category);
	}

	public void delete(int id) {
		super.delete(Category.class, id);
	}

	public List<Category> list(String name) {
		Map<String, Object> params = new HashMap<String ,Object>();
		if(name!=null&&!"".equals(name.trim()))
		params.put("name", "%"+name+"%");
		return super.list(Category.class, params);
	}

	public Category load(int id) {
        return super.load(Category.class, id);
	}

	public List<Category> list() {
		return list(null);
	}

}
