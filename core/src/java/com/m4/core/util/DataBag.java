package com.m4.core.util;

import java.io.Serializable;
import java.util.List;

public class DataBag implements Serializable {
	private List dataList;
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
}
