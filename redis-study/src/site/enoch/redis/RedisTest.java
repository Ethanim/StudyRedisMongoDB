package site.enoch.redis;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import site.enoch.Bean.Dept;
import site.enoch.util.SerializableUtil;

public class RedisTest {
	//连接测试
	@Test
	public void ConnectionTest() {
		Jedis jedis = new Jedis();//连接本机默认端口，可省略链接地址与主机
		System.out.println(jedis.ping());
		jedis.close();
	}
	//查询所有
	@Test
	public void selectAll() {
		Jedis jedis = new Jedis();
		Set<String> keys = jedis.keys("*");// keys *
		for(String s : keys)
			System.out.println(s);
		jedis.close();
	}
	
	//设置并获取数据
	@Test
	public void setData() {
		Jedis jedis = new Jedis();
		jedis.set("name", "tom");//set name tom
		String name = jedis.get("name");// get name
		System.out.println(name);
		jedis.close();
	}
	
	//设置并获取队列
	@Test
	public void setList() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.lpush("mylist", "a b c d e f");//lpush mylist a b c d e f
		List<String> mylist = jedis.lrange("mylist", 0, -1);//lrange mylist 0 -1
		System.out.println(mylist);
		jedis.close();
	}
	//设置并获取 set 集合
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
	//设置 Dept 对象
	@Test
	public void setDept() throws IOException {
		Jedis jedis = new Jedis();
		Dept dept = new Dept(15,"小白");//创建对象
		jedis.set("dept".getBytes(), SerializableUtil.toSerializable(dept));//把序列化后的对象存到 redis 中
		jedis.close();
	}
	
	//获取 Dept 对象并打印
	@Test
	public void getDept() throws Exception {
		Jedis jedis = new Jedis();
		byte[] bytes = jedis.get("dept".getBytes());
		Dept dept = (Dept)SerializableUtil.toObject(bytes);//把序列化后的对象转换回来
		System.out.println(dept);
		jedis.close();
	}
}
