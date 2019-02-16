package com.hzb.util;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hzb.config.WechatConstants;
import com.hzb.entity.AccessToken;

@Component
public class WechatCommonUtil {
	Logger logger = LoggerFactory.getLogger(WechatCommonUtil.class);

	// 获取access_token接口
	private static String token_url = WechatConstants.ACCESS_TOKEN_URL;
	@Autowired
	HttpRequestUtil httpRequestUtil;

	/**
	 * @Description: 获取微信调用高级接口的凭证（access_token）
	 * @Parameters:
	 * @Return:
	 * @Create Date: 2018年9月28日下午12:22:55
	 * @Version: V1.00
	 * @author: houzhibo
	 */
	public AccessToken getAccessToken(String appid, String appsecret) {
		// 将公众号的appid和appsecret替换进url
		String url = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		AccessToken accessToken = new AccessToken();
		// 发起get请求获取凭证
		HttpRequestUtil httpRequestUtil=new HttpRequestUtil();
		JSONObject jsonObject = httpRequestUtil.httpsRequest(url, "GET", null);
		logger.info("获取到的json格式的Token为:" + jsonObject);
		if (jsonObject != null) {
			try {
				accessToken.setAccess_token(jsonObject.getString("access_token"));
				accessToken.setExpires_in(jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				accessToken = null;
				// 获取token失败
				logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
}
