package com.pb.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.pb.entity.Group;
import com.pb.entity.User;

@SuppressWarnings("rawtypes")
public interface UserService {
	Serializable add(User user);
	Map<String, String> login(User user);
	void register(User user);
	List<User> searchAll();
}
