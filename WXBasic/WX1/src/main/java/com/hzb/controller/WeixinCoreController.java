package com.hzb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hzb.util.WeiXinSignUtil;

@RestController
@RequestMapping(value = "/wx")
public class WeixinCoreController {

	private static Logger logger = LoggerFactory.getLogger(WeixinCoreController.class);

	@Autowired
	private WeiXinSignUtil weixinSignUtil;

	/**
	 * @Description: 验证请求是否来自微信服务器
	 * @Parameters: WeixinCoreController
	 * @Return: 返回微信服务器发过来的验证字符
	 * @Create Date:
	 * @Version: V1.00
	 * @author:来日可期
	 */
	@RequestMapping(value = "/validateWx", method = RequestMethod.GET)
	public String WeChatInterface(HttpServletRequest request) throws Exception {
		System.out.println("-------------验证微信服务号信息开始----------");
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		logger.info("signature is :" + signature + "timestamp is" + timestamp + "nonce is :" + nonce);
		if (weixinSignUtil.checkSignature(signature, timestamp, nonce)) {
			System.out.println("-----------验证微信服务号结束------------");
			return echostr;
		} else {
			// 此处可以实现其他逻辑
			logger.warn("不是微信服务器发过来的请求，请小心！");
			return null;
		}
	}
}
