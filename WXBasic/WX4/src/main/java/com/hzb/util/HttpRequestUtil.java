package com.hzb.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hzb.service.MyX509TrustManager;

import net.sf.json.JSONObject;

/**
 * 
 * @Description: 发起https请求并获取结果
 * @Parameters: requestUrl 请求地址。 需要写全地址，即前边的http必须写上，不能只写www.baidu.com这样的。
 *              requestMethod 请求方式（GET、POST） outputStr 我们在发起请求的时候传递参数到所要请求的服务器，
 *              要传递的参数也要看接口文档确定格式，一般是封装成json或xml.
 * @Return: JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
 * @Create Date: 2018年9月19日上午8:20:33
 * @Version: V1.00
 * @author: houzhibo
 */
@Component
public class HttpRequestUtil {
	Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

	public JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		// 初始化一个json对象
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tmManagers = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tmManagers, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory sslSocket = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
			httpsURLConnection.setSSLSocketFactory(sslSocket);

			httpsURLConnection
					.setDoOutput(true); /*
										 * httpUrlConnection.setDoOutput(true);以后就可以使用conn.getOutputStream().write()
										 * httpUrlConnection.setDoInput(true);以后就可以使用conn.getInputStream().read();
										 * get请求用不到conn.getOutputStream()，因为参数直接追加在地址后面，因此默认是false。
										 * post请求（比如：文件上传）需要往服务区传输大量的数据，这些数据是放在http的body里面的，因此需要在建立连接以后，往服务端写数据。
										 * 因为总是使用conn.getInputStream()获取服务端的响应，因此默认值是true。
										 */
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setUseCaches(false);
			// 设置请求方式 GET/POST
			httpsURLConnection.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) { // 不考虑大小写。如果两个字符串的长度相等，并且两个字符串中的相应字符都相等（忽略大小写），则认为这两个字符串是相等的。
				httpsURLConnection.connect();
			}
			// 当有数据需要提交时,往服务器端写内容 也就是发起http请求需要带的参数
			if (null != outputStr) {
				OutputStream outputStream = httpsURLConnection.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 获得输入流 读取服务器端返回的内容
			InputStream inputStream = httpsURLConnection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer stringBuffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				stringBuffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpsURLConnection.disconnect();
			// 将字符串转换为json对象
			jsonObject = JSONObject.fromObject(stringBuffer.toString());
			System.out.println("JSONObject---------------------->" + jsonObject);
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}
		return jsonObject;
	}
}
