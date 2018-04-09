package site.enoch.Bean;

import java.io.Serializable;

public class Dept implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int deptno;
	private String dname;
	
	public Dept() {
		super();
	}
	public Dept(int deptno, String dname) {
		super();
		this.deptno = deptno;
		this.dname = dname;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	@Override
	public String toString() {
		return "Dept [deptno=" + deptno + ", dname=" + dname + "]";
	}
	
}
