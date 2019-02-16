package com.hzb.translate.baidu;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class HttpUtil {
	// 10S
	protected static final int SOCKET_TIMEOUT = 10000;
	protected static final String GET = "GET";

	/**
	 * Get请求，方便到一个url接口来获取结果
	 */
	@SuppressWarnings("deprecation")
	public static JSONObject doGetStr(String url) {
		@SuppressWarnings("resource")
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = defaultHttpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 通过GTT方法进行连接百度翻译的接口
	 */
	public static String get(String host, Map<String, String> params) {
		try {
			// 设置SSLContext
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);
			String sendUrl = getUrlWithQueryString(host, params);
			// 创建URL对象
			URL uri = new URL(sendUrl);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			if (conn instanceof HttpsURLConnection) {
				((HttpsURLConnection) conn).setSSLSocketFactory(sslcontext.getSocketFactory());
			}
			// 设置相应超时（这里设置的是10s）
			conn.setConnectTimeout(SOCKET_TIMEOUT);
			// 设置请求方式
			conn.setRequestMethod(GET);
			// 获取响应码
			int statusCode = conn.getResponseCode();
			if (statusCode != HttpURLConnection.HTTP_OK) {
				System.out.println("Http错误码：" + statusCode);
			}
			// 读取服务器的数据
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			String text = builder.toString();
			// 关闭数据流
			close(br);
			// 关闭数据流
			close(is);
			// 断开连接
			conn.disconnect();
			// 返回响应的内容
			return text;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 对URL进行GET方式的参数封装拼接
	 * 
	 * @param url    接口地址
	 * @param params 对应的参数
	 * @return
	 */
	public static String getUrlWithQueryString(String url, Map<String, String> params) {
		// 如果参数为null
		if (params == null) {
			return url;
		}
		StringBuilder builder = new StringBuilder(url);
		// 对URL进行拼接，因为这里是用的GET方法，所以参数都是在后面进行拼接
		if (url.contains("?")) {
			builder.append("&");
		} else {
			builder.append("?");
		}
		int i = 0;
		// 对URL参数进行拼接
		for (String key : params.keySet()) {
			String value = params.get(key);
			// 过滤空的key
			if (value == null) {
				continue;
			}
			if (i != 0) {
				builder.append('&');
			}
			builder.append(key);
			builder.append('=');
			// 对内容进行UTF-8编码，因为中文传输必须这样
			builder.append(encode(value));
			i++;
		}
		return builder.toString();
	}

	protected static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对输入的字符串进行URL编码, 即转换为%20这种形式
	 * 
	 * @param input 原文
	 * @return URL编码. 如果编码失败, 则返回原文
	 */
	public static String encode(String input) {
		if (input == null) {
			return "";
		}

		try {
			return URLEncoder.encode(input, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return input;
	}

	private static TrustManager myX509TrustManager = new X509TrustManager() {

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	};

}
