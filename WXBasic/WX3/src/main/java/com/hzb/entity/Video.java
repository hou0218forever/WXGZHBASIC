package com.hzb.entity;

/**
 *  *   * @Description: 视频model  * @Parameters:   * @Return:   * @Create Date: 
 *  * @Version: V1.00  * @author: houzhibo  
 */
public class Video {
	// 媒体文件ID
	private String MediaId;
	// 视频消息缩略图的媒体ID
	private String ThumbMediaId;
	private String Title;
	private String Description;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

}
