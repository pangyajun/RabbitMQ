/**
 * @data 2018年3月12日上午10:16:59
 */
package com.oaec.entity;

import java.io.Serializable;

/**
 * @author Mr.pang
 *
 * @date 2018年3月12日 上午10:16:59
 */
public class Spec implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5966765882060129901L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Spec(String name) {
		super();
		this.name = name;
	}

	public Spec() {
		super();
	}

	@Override
	public String toString() {
		return "Spec [name=" + name + "]";
	}
	
	
}
