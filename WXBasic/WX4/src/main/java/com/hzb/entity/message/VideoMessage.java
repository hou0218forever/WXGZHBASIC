package com.hzb.entity.message;

import com.hzb.base.BaseMessage;

/**
 * 
 * @Description:视频 
 * @Parameters: 
 * @Return: 
 * @Create Date: 
 * @Version: V1.00
 * @author: houzhibo
 */
public class VideoMessage extends BaseMessage{
	private Video Video;
 
	public Video getVideo() {
		return Video;
	}
 
	public void setVideo(Video video) {
		this.Video = video;
	}
	
}
