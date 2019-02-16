package com.hzb.entity.menu;
/**
 * @Description: click类型的按钮（有type、name和key3个属性）
 * @Parameters: 
 * @Return: 
 * @Create Date: 2018年3月10日上午9:35:30
 * @Version: V1.00
 * @author: houzhibo
 */
public class ClickButton extends Button {
	private String type;
	private String key;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
 
 
 
