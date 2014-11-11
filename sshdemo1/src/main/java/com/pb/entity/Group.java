package com.pb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name="groups")
@Check(constraints="userNum>=0")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Group {
	private String id;
	private String name;
	private Integer userNum;
	private Set<User> users = new HashSet<User>();
	
	public Group(){	
	}
	public Group(String id,String name,Integer userNum){
		this.id = id;
		this.name = name;
		this.userNum = userNum;
	}
	
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@Column(length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@OneToMany(mappedBy="group",fetch=FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Integer getUserNum() {
		return userNum;
	}
	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}
}
