package com.hzb.translate.baidu;

import org.junit.Test;
import java.io.UnsupportedEncodingException;

/**
 * @author scw
 * @create 2018-01-17 19:29
 * @desc 测试百度翻译功能是否可行
 **/
public class TranslateTest {

	/**
	 * 测试翻译的结果
	 */
	@Test
	public void translateTest() throws UnsupportedEncodingException {
		// 百度翻译API服务的秘钥（自己的秘钥）
		String APP_ID = TranslationUtils.TRANLATE_APP_ID;
		String SECURITY_KEY = TranslationUtils.SECURITY_KEY;
		TransApi api = new TransApi(APP_ID, SECURITY_KEY);
		String query = "hello,how are you";
		System.out.println(api.getTransResult(query, "auto", "auto"));
	}

	/**
	 * 测试翻译的结果
	 */
	@Test
	public void translateTest2() throws UnsupportedEncodingException {
		// 百度翻译API服务的秘钥（自己的秘钥）
//        String APP_ID = "20181114000234463";
//        String SECURITY_KEY = "o1DwKFwajLS5YARGWnOp";
//        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
//        String query = "篮球";
		System.out.println(TranslationUtils.translate("hello,how are you"));
	}
}
