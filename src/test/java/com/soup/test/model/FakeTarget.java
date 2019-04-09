package com.soup.test.model;

import java.util.Date;

public class FakeTarget {
	private Date birthday;
	private int age;
	private SubTarget target;
	
	public FakeTarget() {
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public SubTarget getTarget() {
		return target;
	}
	public void setTarget(SubTarget target) {
		this.target = target;
	}
	@Override
	public String toString() {
		return "FakeTarget [birthday=" + birthday + ", age=" + age + ", target=" + target + "]";
	}
}
