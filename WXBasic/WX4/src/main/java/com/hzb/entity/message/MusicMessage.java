package com.hzb.entity.message;

import com.hzb.base.BaseMessage;

/**
 * 
 * @Description: 音乐消息
 * @Parameters:
 * @Return:
 * @Create Date:
 * @Version: V1.00
 * @author:houzhibo
 */
public class MusicMessage extends BaseMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		this.Music = music;
	}

}
