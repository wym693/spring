package com.pb.service.impl;

import java.io.Serializable;
import java.util.List;

import com.pb.dao.GroupDao;
import com.pb.entity.Group;
import com.pb.service.GroupService;

@SuppressWarnings(value={"rawtypes"})
public class GroupServiceImpl implements GroupService {
	private GroupDao groupDao;

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	public Serializable add(Group group){
		return this.groupDao.save(group);
	}
	public void remove(Group group){
		this.groupDao.delete(group);
	}
	public void modify(Group group){
		this.groupDao.update(group);
	}
	public void addOrModify(Group group){
		this.groupDao.saveOrUpdate(group);
	}
	public Group searchById(Serializable id){
		return this.groupDao.findById(id);
	}
	public List<Group> searchAll(){
		return this.groupDao.findAll();
	}
	public List<Group> searchByProperty(String propertyName, Object value){
		return this.groupDao.findByProperty(propertyName, value);
	}
	
	public Object searchUniqueResult(String hql,Object... paras){
		return this.groupDao.uniqueResult(hql, paras);
	}
	public List searchByHql(String hql,Object... paras){
		return this.groupDao.findByHql(hql, paras);
	}
	public List searchByHqlInCache(String hql,Object... paras){
		return this.groupDao.findByHqlInCache(hql, paras);
	}
	public List searchPage(String hql,int maxResult,int firstResult,Object... paras){
		return this.groupDao.findPage(hql, maxResult, firstResult, paras);
	}
	public List searchBySQLQuery(String sql,Object... paras){
		return this.groupDao.findBySQLQuery(sql, paras);		
	}
	public List searchByNamedParam(String hql,String[] paramNames,Object[] paras){
		return this.groupDao.getHibernateTemplate().findByNamedParam(hql, paramNames, paras);
	}
	
	public void ModifyUserNum(String id,Integer num){
		this.groupDao.updateUserNum(id, num);
	}
}
