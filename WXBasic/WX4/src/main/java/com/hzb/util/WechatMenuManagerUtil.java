package com.hzb.util;

import com.hzb.entity.menu.Button;
import com.hzb.entity.menu.ClickButton;
import com.hzb.entity.menu.ComplexButton;
import com.hzb.entity.menu.Menu;
import com.hzb.entity.menu.ViewButton;

/**
 * @Description: 菜单管理器类
 * @Parameters:
 * @Return:
 * @Create Date: 2018年9月28日下午4:33:24
 * @Version: V1.00
 * @author: houzhibo
 */
public class WechatMenuManagerUtil {
	/**
	 * @Description: 定义菜单结构
	 * @Parameters:
	 * @Return:
	 * @Create Date: 2018年9月28日下午5:36:08
	 * @Version: V1.00
	 * @author: houzhibo
	 */
	public Menu getMenu() {
		//第一列
		ClickButton firstClickButton = new ClickButton();
		firstClickButton.setName("今日歌曲");
		firstClickButton.setKey("jrgq");
		firstClickButton.setType("click");

		//第二列
		ViewButton firstViewButton = new ViewButton();
		firstViewButton.setName("搜索");
		firstViewButton.setType("view");
		firstViewButton.setUrl("http://www.soso.com/");
		Button[] boButtons = { firstClickButton, firstViewButton };
		
		//第三列
		ComplexButton complexButton=new ComplexButton();
		complexButton.setName("菜单");
		complexButton.setSub_button(boButtons);
		
		Menu menu = new Menu();
		menu.setButton(boButtons);

		return menu;
	}
}
