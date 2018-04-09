package site.enoch.redis;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import site.enoch.Bean.Dept;
import site.enoch.util.SerializableUtil;

public class RedisTest {
	//���Ӳ���
	@Test
	public void ConnectionTest() {
		Jedis jedis = new Jedis();//���ӱ���Ĭ�϶˿ڣ���ʡ�����ӵ�ַ������
		System.out.println(jedis.ping());
		jedis.close();
	}
	//��ѯ����
	@Test
	public void selectAll() {
		Jedis jedis = new Jedis();
		Set<String> keys = jedis.keys("*");// keys *
		for(String s : keys)
			System.out.println(s);
		jedis.close();
	}
	
	//���ò���ȡ����
	@Test
	public void setData() {
		Jedis jedis = new Jedis();
		jedis.set("name", "tom");//set name tom
		String name = jedis.get("name");// get name
		System.out.println(name);
		jedis.close();
	}
	
	//���ò���ȡ����
	@Test
	public void setList() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.lpush("mylist", "a b c d e f");//lpush mylist a b c d e f
		List<String> mylist = jedis.lrange("mylist", 0, -1);//lrange mylist 0 -1
		System.out.println(mylist);
		jedis.close();
	}
	//���ò���ȡ set ����
	@Test
	public void setSet() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.hset("myset","tom","1");//hset myset tom 1
		jedis.hset("myset","jack","2");//hset myset jack 2
		jedis.hset("myset","rose","3");//hset myset rose 3
		List<String> lists = jedis.hmget("myset", "tom", "jack", "rose");// hmget myset tom jack rose
		for(String s : lists)
			System.out.println(s);
		jedis.close();
	}
	//���� Dept ����
	@Test
	public void setDept() throws IOException {
		Jedis jedis = new Jedis();
		Dept dept = new Dept(15,"С��");//��������
		jedis.set("dept".getBytes(), SerializableUtil.toSerializable(dept));//�����л���Ķ���浽 redis ��
		jedis.close();
	}
	
	//��ȡ Dept ���󲢴�ӡ
	@Test
	public void getDept() throws Exception {
		Jedis jedis = new Jedis();
		byte[] bytes = jedis.get("dept".getBytes());
		Dept dept = (Dept)SerializableUtil.toObject(bytes);//�����л���Ķ���ת������
		System.out.println(dept);
		jedis.close();
	}
}
