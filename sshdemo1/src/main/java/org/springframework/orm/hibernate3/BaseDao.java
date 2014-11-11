package org.springframework.orm.hibernate3;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

//hibernateTemplate类
@SuppressWarnings("rawtypes")
public interface BaseDao<T> {
	HibernateTemplate getHibernateTemplate();

	//增删查改
	Serializable save(T instance);
	void delete(T instance);
	void update(T instance);
	void saveOrUpdate(T instance);
	T findById(Serializable id);
	
	//查找
	List<T> findAll();
	
	List<T> findByProperty(String propertyName, Object value);
	//根据可变参数查找唯一对象
	Object uniqueResult(final String hql,final Object... paras);//("from User where id=:id and name=:name",1,"张三")
	//根据可变参数查找一批对象
	List findByHql(String hql,Object... paras);
	//执行批量操作 返回受影响的条目
	Integer executeByHql(final String hql,final Object... paras);
	//从缓存里查找对象
	List findByHqlInCache(final String hql,final Object... paras);
	//分页
	List findPage(final String hql,final int maxResult,final int firstResult,final Object... paras);
	//使用纯sql
	List findBySQLQuery(final String sql,final Object... paras);
}
