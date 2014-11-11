package com.pb.dao;

import org.springframework.orm.hibernate3.BaseDao;

import com.pb.entity.Group;

public interface GroupDao extends BaseDao<Group> {
	void updateUserNum(String id,Integer num);
}
