package com.pb.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pb.dao.GroupDao;
import com.pb.dao.UserDao;
import com.pb.entity.Group;
import com.pb.entity.User;
import com.pb.service.UserService;
import com.sun.net.httpserver.Authenticator.Success;

public class UserServiceImpl implements UserService {
	private UserDao userDao;
	private GroupDao groupDao;

	public Serializable add(User user) {
		Group group = user.getGroup();
		this.userDao.save(user);		
		group.setUserNum(group.getUserNum()+1);
		this.groupDao.update(group);
		return null;
	}
	
	public Map<String, String> login(User user){
		Map<String, String> errors = new HashMap<String, String>();
		String hql = "from User where username=?";
		List list = this.userDao.findByHql(hql, user.getUsername());
		if(list.size() == 0){
			errors.put("user.username", "用户名不存在");
		}else{			
			if( !((User)(list.get(0))).getPassword().equals(user.getPassword()) ){
				errors.put("user.password", "密码不正确");
			}
		}		
		return errors;
	}
	
	public List<User> searchAll(){
		return this.userDao.findAll();
	}
	
	public void register(User user){
		this.userDao.save(user);
		this.groupDao.updateUserNum(user.getGroup().getId(), 1);	
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

}
