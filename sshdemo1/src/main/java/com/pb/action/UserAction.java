package com.pb.action;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.pb.entity.Group;
import com.pb.entity.User;
import com.pb.service.GroupService;
import com.pb.service.UserService;

public class UserAction extends ActionSupport{	
	private UserService userService;
	private GroupService groupService;

	private List<User> users;
	
	public String gotoAllUsers(){
		this.users = this.userService.searchAll();
		return SUCCESS;
	}
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public GroupService getGroupService() {
		return groupService;
	}
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
