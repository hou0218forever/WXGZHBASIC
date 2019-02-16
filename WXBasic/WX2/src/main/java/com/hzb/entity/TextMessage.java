package com.hzb.entity;

/**
 * @author: houzhibo
 * @date: 2019年2月15日 下午4:12:46
 * @describe:
 */
public class TextMessage {
	private String fromUserName; // 发送发微信账号

	private String toUserName; // 接收方微信账号

	private String weixinUserName; // 微信用户名

	private String messageType; // 消息类型

	private Long createTime;

	private String msgType;

	private Integer funcFlag;

	private String content;

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getWeixinUserName() {
		return weixinUserName;
	}

	public void setWeixinUserName(String weixinUserName) {
		this.weixinUserName = weixinUserName;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Integer getFuncFlag() {
		return funcFlag;
	}

	public void setFuncFlag(Integer funcFlag) {
		this.funcFlag = funcFlag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
