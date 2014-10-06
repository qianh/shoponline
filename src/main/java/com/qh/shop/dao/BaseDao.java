package com.qh.shop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import com.qh.shop.model.Pager;
import com.qh.shop.model.SystemContext;
import com.qh.shop.util.DaoUtil;
import com.qh.shop.util.MybatisUtil;

public class BaseDao<T> {
	
	public BaseDao() {
	    DaoUtil.diDao(this);
	}

    public void add(T obj){
    	SqlSession session=null;
		try {
			session=MybatisUtil.createSqlSession();
			session.insert(obj.getClass().getName()+".add",obj);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
			session.rollback();
		}finally{
			MybatisUtil.closeSession(session);
		}
    }
    
    public void add(String sqlId,Object obj) {
		SqlSession session = null;
		try {
			session = MybatisUtil.createSqlSession();
			session.insert(sqlId,obj);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			MybatisUtil.closeSession(session);
		}
	}
    
    public void update(T obj){
    	SqlSession session=null;
		try {
			session=MybatisUtil.createSqlSession();
			session.update(obj.getClass().getName()+".update",obj);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
			session.rollback();
		}finally{
			MybatisUtil.closeSession(session);
		}
    }
    
    
    public void delete(Class<T> clz,int id){
    	SqlSession session=null;
		try {
			session=MybatisUtil.createSqlSession();
			session.delete(clz.getName()+".delete", id);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
			session.rollback();
		}finally{
			MybatisUtil.closeSession(session);
		}
    }
    
    public void delete(String sqlId,Map<String,Object> params) {
		SqlSession session = null;
		try {
			session = MybatisUtil.createSqlSession();
			session.delete(sqlId,params);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			MybatisUtil.closeSession(session);
		}
	}
    
    @SuppressWarnings("unchecked")
	public T load(Class<T> clz,int id){
    	SqlSession session = null;
		T t=null;
	    try {
	    session=MybatisUtil.createSqlSession();
		t = (T)session.selectOne(clz.getName()+".load", id);
		}finally{
			MybatisUtil.closeSession(session);
		}
	   return t;	
    }
    
    @SuppressWarnings("unchecked")
	public T loadBySqlId(String sqlId,Map<String,Object> params){
    	SqlSession session = null;
		T t=null;
	    try {
	    session=MybatisUtil.createSqlSession();
		t = (T)session.selectOne(sqlId, params);
		}finally{
			MybatisUtil.closeSession(session);
		}
	   return t;
    }
    
	@SuppressWarnings("unchecked")
	public T loadBySqlId(String sqlId,Object obj){
    	SqlSession session = null;
		T t=null;
	    try {
	    session=MybatisUtil.createSqlSession();
		t = (T)session.selectOne(sqlId, obj);
		}finally{
			MybatisUtil.closeSession(session);
		}
	   return t;
    }
    
    public List<T> list(Class<T> clz,Map<String,Object> params){
    	return this.list(clz.getName()+".list", params);
    }
    
    
    public List<T> list(String sqlId,Map<String,Object> params){
    	List<T> list = null;
    	SqlSession session=null;
    	try{
    	session=MybatisUtil.createSqlSession();
    	list=session.selectList(sqlId, params);
    	}finally{
    		MybatisUtil.closeSession(session);
    	}
    	return list;
    }
    
    public Pager<T> find(Class<T> clz, Map<String,Object> params){
    	return this.find(clz.getName()+".find", params);
    }
    
    public Pager<T> find(String sqlId, Map<String,Object> params){
    	int pageSize=SystemContext.getPageSize();
		int pageOffset=SystemContext.getPageOffset();
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		Pager<T> pages = new Pager<T>();
		SqlSession session=null;
		try {
			session=MybatisUtil.createSqlSession();
			if(params==null) params = new HashMap<String, Object>();
			params.put("pageOffset", pageOffset);
			params.put("pageSize", pageSize);
			params.put("order", order);
			params.put("sort", sort);
			List<T> datas = session.selectList(sqlId, params);
			pages.setDatas(datas);
			pages.setPageSize(pageSize);
			pages.setPageOffset(pageOffset);
			int totalRecord = session.selectOne(sqlId+"_count", params);
			pages.setTotalRecord(totalRecord);
		} finally{
			MybatisUtil.closeSession(session);
		}
		return pages;
    }
}
