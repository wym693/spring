package com.pb.quartz.job;

import java.util.Date;

public class CountSalaryJob {

	
	public void execute(){
		System.out.println("执行计算员工工资的作业"+new Date());
	}
}
