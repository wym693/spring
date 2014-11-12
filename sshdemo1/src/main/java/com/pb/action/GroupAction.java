package com.pb.action;

import com.opensymphony.xwork2.ActionSupport;
import com.pb.service.GroupService;

public class GroupAction extends ActionSupport {
	private GroupService groupService;
	
	

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
}
