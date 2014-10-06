package com.qh.shop.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.qh.shop.dao.ICategoryDao;
import com.qh.shop.dao.IProductDao;
import com.qh.shop.model.Product;
import com.qh.shop.model.ShopDi;
import com.qh.shop.model.ShopException;
import com.qh.shop.model.SystemContext;
import com.qh.shop.util.RequestUtil;


public class ProductServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private ICategoryDao categoryDao;
	private IProductDao productDao;
	
	
	
	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}
	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public IProductDao getProductDao() {
		return productDao;
	}
	@ShopDi
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	@Auth("any")
	public String list(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("products", productDao.find(0, null,0));
		return "product/list.jsp";
	}
	
	@Auth("any")
	public String show(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("product", productDao.load(Integer.parseInt(req.getParameter("id"))));
		return "product/show.jsp";
	}
	
	public String addInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("cs",categoryDao.list());
		return "product/addInput.jsp";
	}
	
	
	
	public String addStockInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("c",productDao.load(Integer.parseInt(req.getParameter("id"))));
		return "product/addStockInput.jsp";
	}
	
	public String addStock(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int number = Integer.parseInt(req.getParameter("number"));
			int id = Integer.parseInt(req.getParameter("id"));
			productDao.addStock(id, number);
		} catch (NumberFormatException e) {
			this.getErrors().put("number", "�������ͱ���Ϊ����");
			req.setAttribute("c",productDao.load(Integer.parseInt(req.getParameter("id"))));
			return "product/addStockInput.jsp";
		}
		return redirpath("product.do?method=list");
	}
	
	public String delete(HttpServletRequest req,HttpServletResponse resp) {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			productDao.delete(id);
		} catch (ShopException e) {
			return this.handleException(e, req);
		}
		return redirpath("product.do?method=list");
	}
	
	public String changeStatus(HttpServletRequest req,HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		productDao.changeStatus(id);
		return redirpath("product.do?method=list");
	}
	
	public String add(HttpServletRequest req,HttpServletResponse resp) throws FileNotFoundException, IOException {
		Product p = (Product)RequestUtil.setParam(Product.class, req);
		p.setStatus(1);
		RequestUtil.validate(Product.class, req);
		int cid = 0;
		try {
			cid = Integer.parseInt(req.getParameter("cid"));
		} catch (NumberFormatException e) {}
		if(cid==0) {
			this.getErrors().put("cid", "��Ʒ������ѡ��");
		}
		if(!this.hasErrors()) {
			//�ļ��ϴ�
			byte[] fs = (byte[])req.getAttribute("fs");
			String fname = req.getParameter("img");
			fname = FilenameUtils.getName(fname);
			RequestUtil.uploadFile(fname, "img", fs, req);

		}
		if(this.hasErrors()) {
			addInput(req,resp);
			return "product/addInput.jsp";
		}
		productDao.add(cid, p);
		return redirpath("product.do?method=list");
	}
	
	public String update(HttpServletRequest req,HttpServletResponse resp) throws FileNotFoundException, IOException {
		Product p = (Product)RequestUtil.setParam(Product.class, req);
		RequestUtil.validate(Product.class, req);
		String img = req.getParameter("img");
		System.out.println(img+"----------------------");
		int cid = 0;
		try {
			cid = Integer.parseInt(req.getParameter("cid"));
		} catch (NumberFormatException e) {}
		if(cid==0) {
			this.getErrors().put("cid", "��Ʒ������ѡ��");
		}
		Product tp = productDao.load(Integer.parseInt(req.getParameter("id")));
		tp.setIntro(p.getIntro());
		tp.setName(p.getName());
		tp.setPrice(p.getPrice());
		tp.setStock(p.getStock());
		boolean updateImg = false;
		if(img==null||img.trim().equals("")) {
			//��ʱ˵�����޸�ͼƬ
		} else {
			//��ʱ˵����Ҫ�޸�ͼƬ
			if(!this.hasErrors()) {
				//�Ƿ�Ҫ�޸��ļ�
				byte[] fs = (byte[])req.getAttribute("fs");
				String fname = req.getParameter("img");
				fname = FilenameUtils.getName(fname);
				RequestUtil.uploadFile(fname, "img", fs, req);
			}
			updateImg = true;
		}
		if(this.hasErrors()) {
			req.setAttribute("p", tp);
			req.setAttribute("cs",categoryDao.list());
			return "product/updateInput.jsp";
		}
		if(updateImg) {
			//��ɾ��ԭ�е�ͼƬ
			String oimg = tp.getImg();
			File f = new File(SystemContext.getRealpath()+"/img/"+oimg);
			f.delete();
			tp.setImg(p.getImg());
		}
		productDao.update(cid, tp);
		return redirpath("product.do?method=list");
	}
	
	public String updateInput(HttpServletRequest req,HttpServletResponse resp) {
		req.setAttribute("cs",categoryDao.list());
		req.setAttribute("p", productDao.load(Integer.parseInt(req.getParameter("id"))));
		return "product/updateInput.jsp";
	}
}
