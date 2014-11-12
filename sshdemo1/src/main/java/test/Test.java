package test;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.pb.entity.Group;
import com.pb.entity.User;
import com.pb.service.GroupService;
import com.pb.service.UserService;
import com.pb.service.impl.GroupServiceImpl;


public class Test {
	/*
	 * 用来生成数据库表结构，并添加测试数据，调用该方法可以重置数据库，方便测试
	 * 该方法需要hibernate.cfg.xml文件
	 */
	public static void createSchema(){
		//hbm2ddl.auto=create
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
		//HiSessionFacotry
		Configuration configuration = new AnnotationConfiguration();
		SessionFactory sf = configuration.configure().buildSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();	
		Group g1 = new Group(null, "管理员",2);
		Group g2 = new Group(null, "钻石会员",1);
		Group g3 = new Group(null, "白金会员",0);
		Group g4 = new Group(null, "黄金会员",0);
		User u1 = new User(null, "admin", "111111",g1);
		User u2 = new User(null, "admin2", "222222",g1);
		User u3 = new User(null, "张三", "33333",g2);
		session.save(g1);
		session.save(g2);
		session.save(g3);
		session.save(g4);
		session.save(u1);
		session.save(u2);
		session.save(u3);
		session.getTransaction().commit();
	}
	
	
	public static void main(String[] args){
		
		//建表
//		Test.createSchema();
		//添加组
//		test_addGroup();
//		//查找所有组
//		test_searchAllGroups();
		
		//不需要添加事务利用hibernate_template的自动提交可以保存一个对象
		
//		addUser();
		
		//当同时在一个方法中进行两个对象的保存操作的时候，就需要通过显示事务来保证数据一并提交
		addTwoGroup();
		
	}
	
	private static void addTwoGroup() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		//获得一个组
		GroupService groupService = (GroupService)context.getBean("groupService");
		
		Group g1 = new Group(null, "普通会员",0);
		Group g2 = new Group(null, "非会员",-1);//在Group类中已经添加了普通约束，人数的数量不能小于0
		//在没有手动添加事务的情况下，一个正常提交，一个无法提交。
		//这个时候需要添加事务，再执行一次
	    groupService.addTowGroup(g1, g2);
	    
		
		
	}


	


	private static void addUser() {
				
		//保存后返回id
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		GroupService groupService = (GroupService)context.getBean("groupService");
		
		Group group=(Group) groupService.searchUniqueResult("from Group where name=?", "管理员");
		
		UserService userServices=(UserService) context.getBean("userService");
		
		User user=new User("张三", "1234",group );
		
		userServices.add(user);
		
		
		
		
		
		
	}


	/*
	 * 测试查询所有用户类型
	 */
	public static void test_searchAllGroups(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		GroupService groupService = (GroupService)context.getBean("groupService");
		List<Group> groups = groupService.searchAll();
		System.out.println("所有Group：");
		for(Group group : groups){
			System.out.println(group.getId()+"  "+group.getName()+"    "+group.getUserNum());
		}
	}
	
	/*
	 * 测试添加用户类型
	 */
	public static void test_addGroup(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		GroupService groupService = (GroupService)context.getBean("groupService");
		System.out.println("添加白银会员");
		Group group = new Group(null, "白银会员", 0);
		groupService.add(group);
	}
}
