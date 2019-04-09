package com.soup.test.model;

public class SubTarget {
	private long len;
	private double s;
	
	public long getLen() {
		return len;
	}
	public void setLen(long len) {
		this.len = len;
	}
	public double getS() {
		return s;
	}
	public void setS(double s) {
		this.s = s;
	}
	@Override
	public String toString() {
		return "SubTarget [len=" + len + ", s=" + s + "]";
	}
	
}
