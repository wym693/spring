package com.pb.dao.impl;

import org.springframework.orm.hibernate3.BaseDaoImpl;

import com.pb.dao.GroupDao;
import com.pb.entity.Group;

public class GroupDaoImpl extends BaseDaoImpl<Group> implements GroupDao {
	/**
	 * 改变Group的User数量
	 * @param num 变化量，减少用负数
	 */
	public void updateUserNum(String id,Integer num){
		String hql = "update Group set userNum=userNum+? where id=?";
		this.executeByHql(hql, num,id);
	}
}
