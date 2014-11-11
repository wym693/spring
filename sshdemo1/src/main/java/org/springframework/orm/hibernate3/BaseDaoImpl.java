package org.springframework.orm.hibernate3;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Dao的基类，封装了绝大多数的数据库操作
 * 命名查询、命名参数查询等在HibernateTemplate已经实现，直接使用即可。
 * 调用存储过程使用命名查询来实现，具体参考Hibernate帮助文档
 * 放在org.springframework.orm.hibernate3下的目的是为了调用HibernateTemplate的protected方法
 *
 * @param <T>
 */
@SuppressWarnings(value={"rawtypes","unchecked"})
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	//实际要保存的类型
	private Class<T> entityClass;
	protected HibernateTemplate hibernateTemplate;
	
	public BaseDaoImpl() {
		//当该类被继承时，它的子类需要初始化T，通过该代码获取T的类型
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];		 
    }
	
	public Serializable save(T instance) {
		return this.hibernateTemplate.save(instance);
	}
	
	public void delete(T instance) {
		this.hibernateTemplate.delete(instance);
	}
	
	public void update(T instance) {
		this.hibernateTemplate.update(instance);
	}
	
	public void saveOrUpdate(T instance){
		//瞬时态则save，游离态则update
		hibernateTemplate.saveOrUpdate(instance);
	};
	
	public T findById(Serializable id){
		T instance = hibernateTemplate.get(entityClass, id);
		return instance;		
	}
	
	/**
	 * 根据HQL查询，支持单值查询，如使用聚合函数查询
	 */
	public List findByHql(String hql,Object... paras) {	
		 return hibernateTemplate.find(hql, paras);
	}
	
	/**
	 * 根据HQL进行增删改，支持单值查询，如使用聚合函数查询
	 * 插入操作只支持INSERT INTO ... SELECT ...形式,不支持INSERT INTO ... VALUES ...形式
	 * @param hql
	 * @param paras
	 * @return 受影响的行数
	 */
	public Integer executeByHql(final String hql,final Object... paras) {	
		//来自Spring 里面的HibernateTemplate
		return this.hibernateTemplate.executeWithNativeSession(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				hibernateTemplate.prepareQuery(query);
				if (paras != null) {
					for (int i = 0; i < paras.length; i++) {
						query.setParameter(i, paras[i]);
					}
				}
				return query.executeUpdate();
			}
		});
	}
	
	/**
	 * 根据HQL使用查询缓存进行查询
	 */	
	public List findByHqlInCache(final String hql,final Object... paras) {		
		return hibernateTemplate.executeWithNativeSession(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);				
				hibernateTemplate.prepareQuery(query);
				query.setCacheable(true);
				if (paras != null) {
					for (int i = 0; i < paras.length; i++) {
						query.setParameter(i, paras[i]);
					}
				}
				return query.list();
			}
		});
	}
	
	/**
	 * 查询数据库中的所有对象
	 */
	public List<T> findAll(){
		String hql = "from "+this.entityClass.getSimpleName();
		List<T> list = hibernateTemplate.find(hql);
		return list;
	};
	
	/**
	 * 根据属性查找
	 */
	public List<T> findByProperty(String propertyName, Object value){
		String hql = "from "+this.entityClass.getSimpleName()+" where "+propertyName+"=?";
		List<T> list = hibernateTemplate.find(hql,value);
		return list;
	}	
	
	/**
	 * 单值查询
	 */
	public Object uniqueResult(final String hql,final Object... paras) {
		return hibernateTemplate.executeWithNativeSession(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				hibernateTemplate.prepareQuery(query);
				if (paras != null) {
					for (int i = 0; i < paras.length; i++) {
						query.setParameter(i, paras[i]);
					}
				}
				return query.uniqueResult();
			}
		});
	}
	
	/**
	 * 分页查询，记录的下标从0开始
	 */
	public List findPage(final String hql,final int maxResult,final int firstResult,final Object... paras) {
		return hibernateTemplate.executeWithNativeSession(new HibernateCallback<List>() {
			public List doInHibernate(Session session){
				Query query = session.createQuery(hql);
				hibernateTemplate.prepareQuery(query);
				query.setMaxResults(maxResult);
				query.setFirstResult(firstResult);
				if(paras != null){
					for(int i = 0;i<paras.length;i++){
						query.setParameter(i, paras[i]);
					}
				}
				return query.list();
			}
		});
	}	

	/**
	 * 使用本地sql语句进行查询，在报表查询、复杂查询时可能用到
	 * @param sql
	 * @param paras
	 * @return
	 */
	public List findBySQLQuery(final String sql,final Object... paras) {
		return hibernateTemplate.executeWithNativeSession(new HibernateCallback<List>() {
			public List doInHibernate(Session session){
				SQLQuery query = session.createSQLQuery(sql);				
				if(paras != null){
					for(int i = 0;i<paras.length;i++){
						query.setParameter(i, paras[i]);
					}
				}
				return query.list();
			}
		});
	}
	
	/**
	 * 将对象转化为瞬时态，只能在Transaction激活的时候使用
	 * @param instance
	 */
	private void convertToTransient(T instance) {
		hibernateTemplate.getSessionFactory().getCurrentSession().lock(instance, LockMode.NONE);
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	//还可以实现使用jdbc接口查询等功能
}
