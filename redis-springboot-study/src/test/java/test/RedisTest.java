package test;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import site.enoch.MyBootApplication;
import site.enoch.Bean.Dept;

public class RedisTest {
	@Test
	public void setDept() {
		ApplicationContext ac = SpringApplication.run(MyBootApplication.class);//启动项目
		RedisTemplate template = ac.getBean("redisTemplate",RedisTemplate.class);//获取 RedisTemplate 对象
		Dept dept = new Dept(10, "Hello SpringBoot");//创建 Dept 对象
		template.opsForValue().set("spring", dept);//把对象设置到 Redis 服务器里，自动序列化
	}
	@Test
	public void getDept() {
		ApplicationContext ac = SpringApplication.run(MyBootApplication.class);
		RedisTemplate template = ac.getBean("redisTemplate",RedisTemplate.class);
		Dept dept = (Dept)template.opsForValue().get("spring");//获取对象，自动反序列化
		System.out.println(dept);
	}
}
