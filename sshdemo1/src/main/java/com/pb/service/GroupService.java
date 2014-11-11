package com.pb.service;

import java.io.Serializable;
import java.util.List;
import com.pb.entity.Group;

@SuppressWarnings("rawtypes")
public interface GroupService {
	Serializable add(Group group);
	void remove(Group group);
	void modify(Group group);
	void addOrModify(Group group);
	Group searchById(Serializable id);
	List<Group> searchAll();
	List<Group> searchByProperty(String propertyName, Object value);
	
	Object searchUniqueResult(String hql,Object... paras);
	List searchByHql(String hql,Object... paras);
	List searchByHqlInCache(String hql,Object... paras);	
	List searchPage(String hql,int maxResult,int firstResult,Object... paras);
	List searchBySQLQuery(String sql,Object... paras);
	List searchByNamedParam(String hql,String[] paramNames,Object[] paras);
	void ModifyUserNum(String id,Integer num);
}
