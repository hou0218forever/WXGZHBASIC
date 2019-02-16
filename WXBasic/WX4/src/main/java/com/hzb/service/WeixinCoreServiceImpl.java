package com.hzb.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzb.entity.message.Image;
import com.hzb.entity.message.ImageMessage;
import com.hzb.entity.message.Music;
import com.hzb.entity.message.MusicMessage;
import com.hzb.entity.message.Video;
import com.hzb.entity.message.VideoMessage;
import com.hzb.entity.message.Voice;
import com.hzb.entity.message.VoiceMessage;
import com.hzb.entity.message.WeixinMessageInfo;
import com.hzb.translate.baidu.TranslationUtils;
import com.hzb.translate.youdao.YouDaoAPI;
import com.hzb.util.EmojiUtil;
import com.hzb.util.TextMessage;
import com.hzb.util.WeixinMessageModelUtil;
import com.hzb.util.WeixinMessageUtil;
import com.hzb.weather.WeatherInfo;

/**
 * <p>
 * Company: B505信息技术研究所
 * </p>
 * 
 * @Description: 微信消息处理核心service实现类
 * @Create Date: 2017年10月10日下午3:33:16
 * @Version: V1.00
 * @Author: 来日可期
 */
@Service("weixinCoreService")
public class WeixinCoreServiceImpl implements WeixinCoreService {

	private static Logger logger = LoggerFactory.getLogger(WeixinCoreServiceImpl.class);

	@Autowired
	private WeixinMessageUtil weixinMessageUtil;
	@Autowired
	private WeixinMessageInfo weixinMessageInfo;
	@Autowired
	private WeixinMessageModelUtil weixinMessageModelUtil;
//	@Autowired
//	private UserDao userDao;
//	@Autowired
//	private DataProcess dataProcess;

	@Override
	public String weixinMessageHandelCoreService(HttpServletRequest request, HttpServletResponse response) {

		logger.info("------------微信消息开始处理-------------");
		// 返回给微信服务器的消息,默认为null
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = null;
			// xml分析
			// 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
			Map<String, String> map = weixinMessageUtil.parseXml(request);
			// 发送方账号
			String fromUserName = map.get("FromUserName");
			weixinMessageInfo.setFromUserName(fromUserName);
			System.out.println("fromUserName--->" + fromUserName);
			// 接受方账号（公众号）
			String toUserName = map.get("ToUserName");
			weixinMessageInfo.setToUserName(toUserName);
			System.out.println("toUserName----->" + toUserName);
			// 消息类型
			String msgType = map.get("MsgType");
			weixinMessageInfo.setMessageType(msgType);
			logger.info("fromUserName is:" + fromUserName + " toUserName is:" + toUserName + " msgType is:" + msgType);

			// 默认回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(weixinMessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 分析用户发送的消息类型，并作出相应的处理
			// 文本消息
			if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
//				TextMessage textMessage = new TextMessage();
				String content = map.get("Content");
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(weixinMessageUtil.RESP_MESSAGE_TYPE_TEXT);
				respContent = "亲，这是文本消息！";
				
				EmojiUtil emojiUtil = new EmojiUtil();
				String unicodeEmoji = emojiUtil.filterEmoji(content); //unicode编码的Emoji
				
				//表情
				if(content.contains("/:")  || content.contains("\\:")  || content.contains("[") && content.contains("]") || unicodeEmoji.contains("\\")){
				    textMessage.setContent(content);
                    respMessage = weixinMessageUtil.textMessageToXml(textMessage);
				}
				else if ("音乐".equals(content)) {
					MusicMessage musicMessage = new MusicMessage();
					musicMessage.setToUserName(fromUserName);
					musicMessage.setFromUserName(toUserName);
					musicMessage.setCreateTime(new Date().getTime());
					musicMessage.setMsgType(weixinMessageUtil.RESP_MESSAGE_TYPE_MUSIC);

					Music music = new Music();
					music.setTitle("还是想要我们在一起");
					music.setDescription("新歌首发，多听听歌，棒棒哒");
					music.setMusicUrl(
							"http://www.kugou.com/song/#hash=BB621950061B524C80DDC402CF29C91F&album_id=14976283");
					music.setHQMusicUrl(
							"http://www.kugou.com/song/#hash=BB621950061B524C80DDC402CF29C91F&album_id=14976283");
//					music.setThumbMediaId("");
					musicMessage.setMusic(music);
					respMessage = weixinMessageUtil.musicMessageToXml(musicMessage);
				}else if(content.contains("天气") && !"".equals(content)){
	                if(content.contains(":")){
	                	String cityName = content.substring(content.lastIndexOf(":")+1,content.length());
	                    WeatherInfo weather = new WeatherInfo();
	                    String weaInfo = weather.getWeatherInfo(cityName);
	                    textMessage.setContent(weaInfo);
	                    respMessage = weixinMessageUtil.textMessageToXml(textMessage);
	                }else{
	                    String notice = "查询天气的正确姿势: 天气:城市名\n请输入正确的格式哟~";
	                    textMessage.setContent(notice);
	                    respMessage = weixinMessageUtil.textMessageToXml(textMessage);
	                }
	            }
				else if(content.contains("有道翻译") && !"".equals(content)){
	                if(content.contains(":")){
	                    String word = content.substring(content.lastIndexOf(":")+1,content.length()).trim();
	                    YouDaoAPI translateInfo = new YouDaoAPI();
	                    String weaInfo = translateInfo.translate(word);
	                    textMessage.setContent(weaInfo);
	                    respMessage = weixinMessageUtil.textMessageToXml(textMessage);
	                }else{
	                    String notice = "翻译的正确姿势: 翻译:翻译文本\n请输入正确的格式哟~";
	                    textMessage.setContent(notice);
	                    respMessage = weixinMessageUtil.textMessageToXml(textMessage);
	                }
				}
				else if(content.contains("百度翻译") && !"".equals(content)){
	                if(content.contains(":")){
	                    String word = content.substring(content.lastIndexOf(":")+1,content.length()).trim();
	                    String translate = TranslationUtils.translate(word);
	                    textMessage.setContent(translate);
	                    respMessage = weixinMessageUtil.textMessageToXml(textMessage);
	                }else{
	                    String notice = "翻译的正确姿势: 翻译:翻译文本\n请输入正确的格式哟~";
	                    textMessage.setContent(notice);
	                    respMessage = weixinMessageUtil.textMessageToXml(textMessage);
	                }
				}
				else {
					textMessage.setContent(content);
					respMessage = weixinMessageUtil.textMessageToXml(textMessage);
				}
			}
			

			// 图片消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
				ImageMessage imageMessage = new ImageMessage();
				imageMessage.setToUserName(fromUserName);
				imageMessage.setFromUserName(toUserName);
				imageMessage.setCreateTime(new Date().getTime());
				imageMessage.setMsgType(weixinMessageUtil.REQ_MESSAGE_TYPE_IMAGE);
				Image Image = new Image();
				Image.setMediaId(map.get("MediaId"));
				imageMessage.setImage(Image);
				respMessage = weixinMessageUtil.imageMessageToXml(imageMessage);
			}

			// 语音消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是语音消息！";
				VoiceMessage voiceMessage = new VoiceMessage();
				voiceMessage.setToUserName(fromUserName);
				voiceMessage.setFromUserName(toUserName);
				voiceMessage.setCreateTime(new Date().getTime());
				voiceMessage.setMsgType(weixinMessageUtil.REQ_MESSAGE_TYPE_VOICE);
				Voice voice = new Voice();
				voice.setMediaId(map.get("MediaId"));
				voiceMessage.setVoice(voice);
				respMessage = weixinMessageUtil.voiceMessageToXml(voiceMessage);
			}

			// 视频消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频消息！";
				VideoMessage videoMessage = new VideoMessage();
				videoMessage.setToUserName(fromUserName);
				videoMessage.setFromUserName(toUserName);
				videoMessage.setCreateTime(new Date().getTime());
				videoMessage.setMsgType(weixinMessageUtil.REQ_MESSAGE_TYPE_VIDEO);
				Video video = new Video();
				video.setMediaId(map.get("MediaId"));
				video.setThumbMediaId(map.get("ThumbMediaId"));
				video.setTitle("哈哈");
				video.setDescription("hhh");
				videoMessage.setVideo(video);
				respMessage = weixinMessageUtil.videoMessageToXml(videoMessage);
			}

			// 地理位置消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
				textMessage.setContent(respContent);
				respMessage = weixinMessageUtil.textMessageToXml(textMessage);
			}

			// 链接消息
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
				textMessage.setContent(respContent);
				respMessage = weixinMessageUtil.textMessageToXml(textMessage);
			}

			// 事件推送(当用户主动点击菜单，或者扫面二维码等事件)
			else if (msgType.equals(weixinMessageUtil.REQ_MESSAGE_TYPE_EVENT)) {

				// 事件类型
				String eventType = map.get("Event");
				System.out.println("eventType------>" + eventType);
				// 关注
				if (eventType.equals(weixinMessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respMessage = weixinMessageModelUtil.followResponseMessageModel(weixinMessageInfo);
				}
				// 取消关注
				else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					weixinMessageModelUtil.cancelAttention(fromUserName);
				}
				// 扫描带参数二维码
				else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_SCAN)) {
					System.out.println("扫描带参数二维码");
				}
				// 上报地理位置
				else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_LOCATION)) {
					System.out.println("上报地理位置");
				}
				// 自定义菜单（点击菜单拉取消息）
				else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_CLICK)) {

					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = map.get("EventKey");
					System.out.println("eventKey------->" + eventKey);

				}
				// 自定义菜单（(自定义菜单URl视图)）
				else if (eventType.equals(weixinMessageUtil.EVENT_TYPE_VIEW)) {
					System.out.println("处理自定义菜单URI视图");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统出错");
			System.err.println("系统出错");
			respMessage = null;
		} finally {
			if (null == respMessage) {
				respMessage = weixinMessageModelUtil.systemErrorResponseMessageModel(weixinMessageInfo);
			}
		}

		return respMessage;
	}

}
