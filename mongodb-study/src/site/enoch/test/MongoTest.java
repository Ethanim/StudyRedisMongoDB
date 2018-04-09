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
		MongoClient mongo = new MongoClient("localhost", 27017);//���� MongoClient ����
		List<String> databaseNames = mongo.getDatabaseNames();//��ȡ���ݿ����б�
		for(String name : databaseNames) //�������
			System.out.println("����:" + name);
		mongo.close();
	}
	
	@Test
	public void insertDept() throws Exception {
		MongoClient mongo = new MongoClient();//���ӱ�����Ĭ�϶˿ڿ���ʡ��
		DB db = mongo.getDB("java"); // use java
		//Set<String> collectionNames = db.getCollectionNames();//��ȡ���ݿ��м�������
		DBCollection dept = db.getCollection("dept");// ��ȡ dept ����
		BasicDBObject dbObj = new BasicDBObject(); // ���� BasicDBOject ����
		dbObj.put("deptno", 30); //��������
		dbObj.put("dname", "����");
		dbObj.put("loc", "�Ϻ�");
		dept.insert(dbObj); //��ӽ����� db.dept.insert()
		mongo.close();
	}
	
	@Test
	public void selectDept() throws Exception {
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("java");
		DBCollection dept = db.getCollection("dept");
		DBCursor cursor = dept.find(); //db.dept.find()
		while(cursor.hasNext()) {//�����α�
			DBObject obj = cursor.next();
			System.out.println(obj.get("deptno") + " " + obj.get("dname") + " " + obj.get("loc"));
		}
		System.out.println("--------------");
		BasicDBObject dbObj = new BasicDBObject(); //{"loc":"����"}
		dbObj.put("loc", "����");
		DBCursor cursor2 = dept.find(dbObj); //db.dept.find({"loc":"����"})
		while(cursor2.hasNext()) {//�����α�
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
		BasicDBObject q = new BasicDBObject();//�����޸���������
		q.put("deptno", 20);//{"deptno":20} �����޸�����
		BasicDBObject dbObj = new BasicDBObject(); //������Ҫ�޸Ķ���
		BasicDBObject attr = new BasicDBObject(); //�����޸����ݶ���
		attr.put("loc", "�����г�����");//{"loc":"�����г�����"} �����޸�����
		dbObj.put("$set", attr); //���þֲ�����
		
		dept.update(q, dbObj); //db.dept.update({"deptno":20},{$set:{"loc":"�����г�����"}})
		mongo.close();
	}
	
	@Test
	public void deleteDept() throws Exception {
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("java");
		DBCollection dept = db.getCollection("dept");
		BasicDBObject dbObj = new BasicDBObject(); //����ɾ����������
		dbObj.put("loc", "�Ϻ�"); //���ɾ������
		dept.remove(dbObj); //��������ɾ�� 
	}
}
