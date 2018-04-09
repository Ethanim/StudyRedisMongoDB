package site.enoch.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import site.enoch.BootApplication;
import site.enoch.entity.Emp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {BootApplication.class})
public class MongoTest {
	
	//注入 MongoTemplate
	@Autowired
	private MongoTemplate mongo;
	
	@Test
	public void insertEmp() {
		Emp emp = new Emp(1,"李四",22,1);
		//将 emp 对象写入到 mongodb
		mongo.insert(emp);//默认类型名首字母小写做集合名
	}
	
	@Test
	public void selectAllEmp() {
		List<Emp> emps = mongo.findAll(Emp.class);
		for(Emp emp : emps)
			System.out.println(emp);
	}
	
	@Test
	public void selectEmp() {
		Query query = new Query();
		query.addCriteria(Criteria.where("no").is(1));//where no = 1
		List<Emp> emps = mongo.find(query, Emp.class);
		for(Emp emp : emps)
			System.out.println(emp);
	}
	
	@Test
	public void deleteEmp() {
		Query query = new Query();
		query.addCriteria(Criteria.where("no").is(1));//where no = 1
		mongo.remove(query,Emp.class);//按指定条件删除
	}
	
	@Test
	public void updateEmp() {
		Query query = new Query();
		query.addCriteria(Criteria.where("no").is(1));
		
		Update update = new Update();
		update.set("name", "赵六");
		
		//mongo.updateFirst(query, update, Emp.class); // 更新一条
		mongo.updateMulti(query, update, Emp.class); // 更新多条
	}
}
