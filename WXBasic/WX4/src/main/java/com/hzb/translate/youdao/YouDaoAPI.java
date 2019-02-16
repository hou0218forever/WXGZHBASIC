package com.hzb.translate.youdao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class YouDaoAPI {
    public String translate(String q)throws Exception{
        //应用申请的key
        String appKey ="2d156317f9da5d91";
        //要翻译的文本 必须是UTF-8编码
        String query = q;
        //随机数
        String salt = String.valueOf(System.currentTimeMillis());
        //源语言
        String from = "auto";
        //目标语言
        String to = "auto";
        //签名，通过md5(appkey+q+salt+密钥)生成
        String sign = md5(appKey + query + salt+"PXwLK5mGbARMsbtjIfpZBM7sDwt40YAL");

        Map params = new HashMap();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("sign", sign);
        params.put("salt", salt);
        params.put("appKey", appKey);
        return requestForHttp("http://openapi.youdao.com/api", params);
    }

    public  String requestForHttp(String url,Map requestParams) throws Exception{
        String result = null; //翻译后的JSON字符串
        StringBuffer sb = new StringBuffer(); //处理后的返回结果

        CloseableHttpClient httpClient = HttpClients.createDefault();
        /**HttpPost*/
        HttpPost httpPost = new HttpPost(url);

        List params = new ArrayList();
        Iterator it = requestParams.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> en = (Entry)it.next();
            String key = en.getKey();
            String value = en.getValue();
            if (value != null) {
                params.add(new BasicNameValuePair(key, value));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        /**HttpResponse*/
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try{
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "utf-8");
            //EntityUtils.consume(httpEntity);

            /*处理JSON字符串为实体对象TranslateResponseDto*/
            TranslateResponseDto dto = new TranslateResponseDto();
            dto=JSON.parseObject(result, TranslateResponseDto.class);

            /*遍历实体类的每个字段,拼接为字符串返回给用户*/
            if(!"".equals(dto.getQuery()) && dto.getQuery() != null){
                sb.append("原文:"+dto.getQuery() + "\n");
            }
            if(!"".equals(dto.getTranslation()) && dto.getTranslation() != null){
                sb.append("翻译结果:");
                List<String> translation = dto.getTranslation();
                for(String s : translation){
                    sb.append(s + "\n");
                }
            }
            if(!"".equals(dto.getBasic()) && dto.getBasic() != null){
                Map<String, List<String>> basic = dto.getBasic();
                sb.append("词义:");
                for (Map.Entry<String, List<String>> entry : basic.entrySet()) {
                    sb.append(entry.getValue()+ "\n");
                }
            }
            if(!"".equals(dto.getErrorCode()) && dto.getErrorCode() != null && !"0".equals(dto.getErrorCode())){
                if("103".equals(dto.getErrorCode())){
                    sb.append("翻译出错:翻译文本过长\n");
                }else{
                    sb.append("翻译出错:错误代码为"+dto.getErrorCode()+"\n");
                }
            }


        }finally{
            try{
                if(httpResponse!=null){
                    httpResponse.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 生成32位MD5摘要
     * @param string
     * @return
     */
    public static String md5(String string) {
        if(string == null){
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try{
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            return null;
        }
    }

    /**
     * 根据api地址和参数生成请求URL
     * @param url
     * @param params
     * @return
     */
    public static String getUrlWithQueryString(String url, Map params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : (List<String>)params.keySet()) {
            String value = (String) params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }

    /**
     * 进行URL编码
     * @param input
     * @return
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
}


