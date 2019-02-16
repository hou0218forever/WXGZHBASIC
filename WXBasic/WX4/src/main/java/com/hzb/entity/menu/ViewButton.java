package com.hzb.entity.menu;
/**
 * @Description: view类型的按钮(有type、name、url三个属性)
 * @Parameters: 
 * @Return: 
 * @Create Date: 2018年3月10日上午9:39:06
 * @Version: V1.00
 * @author: houzhibo
 */
public class ViewButton extends Button{
 
	public String type;
	public String url;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
 
 
 
