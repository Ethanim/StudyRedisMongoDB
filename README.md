大家可以去我的个人博客看看，[Redis 学习笔记](http://www.enoch.site/2018/03/23/RedisStudy/)，
[MongoDB 学习笔记](http://www.enoch.site/2018/04/08/MongoDBStudy/)，
[Reids 结合 Java 与 SpringBoot 操作](http://www.enoch.site/2018/04/09/RedisJavaOperation/)，
[MongoDB 结合 Java 与 SpringBoot 操作](http://www.enoch.site/2018/04/09/MongoDBJavaOperation/)
 
## Java 原生操作 MongoDB

1.创建 Dynamic Web Project 项目，粘贴 mongo-java-driver-2.13.2.jar 包到 lib 中
2.创建测试类,具体如下

### 获取数据库列表
相当于命令 `show databases`

	@Test
	public void selectDataBaseNames() throws Exception {
		MongoClient mongo = new MongoClient("localhost", 27017);//创建 MongoClient 对象
		List<String> databaseNames = mongo.getDatabaseNames();//获取数据库名列表
		for(String name : databaseNames) //遍历输出
			System.out.println("库名:" + name);
		mongo.close();
	}

测试结果

	库名:admin
	库名:java
	库名:javaDB
	库名:local

### 添加数据到集合
相当于命令 `db.dept.insert()`

	@Test
	public void insertDept() throws Exception {
		MongoClient mongo = new MongoClient();//连接本机和默认端口可以省略
		DB db = mongo.getDB("java"); // use java
		//Set<String> collectionNames = db.getCollectionNames();//获取数据库中集合名字
		DBCollection dept = db.getCollection("dept");// 获取 dept 集合
		BasicDBObject dbObj = new BasicDBObject(); // 创建 BasicDBOject 对象
		dbObj.put("deptno", 20); //放入数据
		dbObj.put("dname", "行政部");
		dbObj.put("loc", "北京");
		dept.insert(dbObj); //添加进集合 db.dept.insert()
		mongo.close();
	}

通过 MongoDB 客户端查看结果如下

![](/img/MonoDBJavaOperation1.png)

### 查询集合
下面分别是查询全部`db.dept.find()`
和带参查询`db.dept.find(json字符串)`

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

测试结果

	20 行政部 北京
	30 财务部 上海
	--------------
	20 行政部 北京

### 修改集合

把部门编号为 20 的对象地址修改为北京市朝阳区，
代码如下，对应命令为`db.dept.update({"deptno":20},{$set:{"loc":"北京市朝阳区"}})`

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

运行后，再运行查询的方法，结果如下

	20 行政部 北京市朝阳区
	30 财务部 上海
	--------------

### 删除集合

删除集合中 loc 为上海的数据，对应命令为`db.dept.remove({"loc":"上海"})`
	
	@Test
	public void deleteDept() throws Exception {
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("java");
		DBCollection dept = db.getCollection("dept");
		BasicDBObject dbObj = new BasicDBObject(); //创建删除条件对象
		dbObj.put("loc", "上海"); //添加删除条件
		dept.remove(dbObj); //根据条件删除 
	}

运行后，再运行查询的方法，结果如下

	20 行政部 北京市朝阳区
	--------------

## SpringBoot 中操作 MongoDB

在 java 原生操作中，需要用到 json 的地方都得封装成 BasicDBObject 类型的对象，而在 SpringBoot 中对其做了封装，直接用普通对象就可以了

1.创建 maven 项目，引入对应 jar 包，配置 pom.xml，具体如下

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.4.7.RELEASE</version>
	</parent>

	<dependencies>
		<!-- spring test -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
		</dependency>
		<!-- mongodb -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
	</dependencies>

2.在 application.properties 里配置 mongodb连接信息，具体如下

	spring.data.mongodb.uri=mongodb://localhost:27017/java


3.配置主启动类

	@SpringBootApplication
	public class BootApplication {
		public static void main(String[] args) {
			SpringApplication.run(BootApplication.class);
		}
	}

4.实体类

	public class Emp {
		private int no;
		private String name;
		private int age;
		private int deptNo;
		//省略无参、有参构造方法，get、set 方法。
	｝

5.测试类，SpringBoot 框架封装了 MongoTemplate，方便操作，增删改查如下：

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
