package com.hzb.util;

/**
 * @author: houzhibo
 * @date: 2019年2月15日 下午4:12:46
 * @describe:
 */
public class TextMessage {
	private String FromUserName; // 发送发微信账号

	private String ToUserName; // 接收方微信账号

	private String WeixinUserName; // 微信用户名

	private String MessageType; // 消息类型

	private Long CreateTime;

	private String MsgType;

	private Integer FuncFlag;

	private String Content;

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}

	public String getWeixinUserName() {
		return WeixinUserName;
	}

	public void setWeixinUserName(String weixinUserName) {
		this.WeixinUserName = weixinUserName;
	}

	public String getMessageType() {
		return MessageType;
	}

	public void setMessageType(String messageType) {
		this.MessageType = messageType;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		this.CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}

	public Integer getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(Integer funcFlag) {
		this.FuncFlag = funcFlag;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

}
