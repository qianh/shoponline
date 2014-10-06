package com.qh.shop.dao;

import com.qh.shop.model.CartProduct;

public interface ICartProductDao {
	public void add(CartProduct cp,int oid);
	public void delete(int oid);
}
