package com.demo.vo;

import java.util.List;

/**
 * easyui表格grid插件Model
 * 
 * @author sdyang
 * @date 2016年2月19日 下午2:53:36
 */
public class Grid {

	private int total;

	private List<?> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
