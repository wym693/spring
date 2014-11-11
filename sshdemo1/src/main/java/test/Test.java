package test;

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
		test_searchAllGroups();
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
