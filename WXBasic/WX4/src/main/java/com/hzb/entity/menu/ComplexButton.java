package com.hzb.entity.menu;
/** 
 * @Description: 复合类型的按钮（也就是含有子菜单的一级菜单）
 * @Parameters: 
 * @Return: 
 * @Create Date: 2018年3月10日上午9:43:13
 * @Version: V1.00
 * @author: houzhibo
 */
public class ComplexButton extends Button{
	private Button[] sub_button;
 
	public Button[] getSub_button() {
		return sub_button;
	}
 
	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
 
}
 
 
 
