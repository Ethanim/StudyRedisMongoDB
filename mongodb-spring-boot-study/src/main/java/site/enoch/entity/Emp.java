package site.enoch.entity;

public class Emp {
	private int no;
	private String name;
	private int age;
	private int deptNo;

	public Emp() {
		super();
	}

	public Emp(int no, String name, int age, int deptNo) {
		super();
		this.no = no;
		this.name = name;
		this.age = age;
		this.deptNo = deptNo;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	@Override
	public String toString() {
		return "Emp [no=" + no + ", name=" + name + ", age=" + age + ", deptNo=" + deptNo + "]";
	}

}
