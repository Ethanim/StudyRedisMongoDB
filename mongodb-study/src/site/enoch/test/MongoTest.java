package site.enoch.test;

import java.util.List;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoTest {
	
	@Test
	public void selectDataBaseNames() throws Exception {
		MongoClient mongo = new MongoClient("localhost", 27017);//创建 MongoClient 对象
		List<String> databaseNames = mongo.getDatabaseNames();//获取数据库名列表
		for(String name : databaseNames) //遍历输出
			System.out.println("库名:" + name);
		mongo.close();
	}
	
	@Test
	public void insertDept() throws Exception {
		MongoClient mongo = new MongoClient();//连接本机和默认端口可以省略
		DB db = mongo.getDB("java"); // use java
		//Set<String> collectionNames = db.getCollectionNames();//获取数据库中集合名字
		DBCollection dept = db.getCollection("dept");// 获取 dept 集合
		BasicDBObject dbObj = new BasicDBObject(); // 创建 BasicDBOject 对象
		dbObj.put("deptno", 30); //放入数据
		dbObj.put("dname", "财务部");
		dbObj.put("loc", "上海");
		dept.insert(dbObj); //添加进集合 db.dept.insert()
		mongo.close();
	}
	
	@Test
	public void selectDept() throws Exception {
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("java");
		DBCollection dept = db.getCollection("dept");
		DBCursor cursor = dept.find(); //db.dept.find()
		while(cursor.hasNext()) {//遍历游标
			DBObject obj = cursor.next();
			System.out.println(obj.get("deptno") + " " + obj.get("dname") + " " + obj.get("loc"));
		}
		System.out.println("--------------");
		BasicDBObject dbObj = new BasicDBObject(); //{"loc":"北京"}
		dbObj.put("loc", "北京");
		DBCursor cursor2 = dept.find(dbObj); //db.dept.find({"loc":"北京"})
		while(cursor2.hasNext()) {//遍历游标
			DBObject obj = cursor2.next();
			System.out.println(obj.get("deptno") + " " + obj.get("dname") + " " + obj.get("loc"));
		}
		mongo.close();
	}
	
	@Test
	public void updateDept() throws Exception {
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("java");
		DBCollection dept = db.getCollection("dept");
		BasicDBObject q = new BasicDBObject();//创建修改条件对象
		q.put("deptno", 20);//{"deptno":20} 放入修改条件
		BasicDBObject dbObj = new BasicDBObject(); //创建所要修改对象
		BasicDBObject attr = new BasicDBObject(); //创建修改内容对象
		attr.put("loc", "北京市朝阳区");//{"loc":"北京市朝阳区"} 放入修改内容
		dbObj.put("$set", attr); //设置局部更新
		
		dept.update(q, dbObj); //db.dept.update({"deptno":20},{$set:{"loc":"北京市朝阳区"}})
		mongo.close();
	}
	
	@Test
	public void deleteDept() throws Exception {
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("java");
		DBCollection dept = db.getCollection("dept");
		BasicDBObject dbObj = new BasicDBObject(); //创建删除条件对象
		dbObj.put("loc", "上海"); //添加删除条件
		dept.remove(dbObj); //根据条件删除 
	}
}
