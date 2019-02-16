package com.hzb.entity.menu;
/**
 * @Description: 按钮的基类（每个按钮对象都有一个共同的name属性，
 * 因此需要定义一个按钮对象的基类，所有按钮对象都需要继承该类）
 * @Parameters: 
 * @Return: 
 * @Create Date: 2018年3月10日上午9:30:27
 * @Version: V1.00
 * @author: houzhibo
 */
public class Button {
	private String name;
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
 
}
 
 
 
