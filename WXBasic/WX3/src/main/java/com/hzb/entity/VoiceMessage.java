package com.hzb.entity;

import com.hzb.base.BaseMessage;

/**
 * 
 * @Description: 语音消息
 * @Parameters: 
 * @Return: 
 * @Create Date: 
 * @Version: V1.00
 * @author: 来日可期
 */
public class VoiceMessage extends BaseMessage{
	private Voice Voice;
 
	public Voice getVoice() {
		return Voice;
	}
 
	public void setVoice(Voice voice) {
		this.Voice = voice;
	}
	
}
