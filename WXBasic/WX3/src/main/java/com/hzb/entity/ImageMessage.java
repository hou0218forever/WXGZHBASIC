package com.hzb.entity;

import com.hzb.base.BaseMessage;

/**
 * 
 * @Description: 图片消息
 * @Parameters:
 * @Return:
 * @Create Date:
 * @Version: V1.00
 * @author: houzhibo
 */
public class ImageMessage extends BaseMessage {
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		this.Image = image;
	}

}
