package com.hzb.translate.youdao;

import java.util.List;
import java.util.Map;

/**
* @author: houzhibo
* @date: 2019年2月16日 下午4:27:56
* @describe:
*/
public class TranslateResponseDto {

	private String web;
	private String query;
	private List<String> translation;
	private String errorCode;
	private String dict;
	private String webdict;
	private Map<String, List<String>> basic;
	private String l;
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List<String> getTranslation() {
		return translation;
	}
	public void setTranslation(List<String> translation) {
		this.translation = translation;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getDict() {
		return dict;
	}
	public void setDict(String dict) {
		this.dict = dict;
	}
	public String getWebdict() {
		return webdict;
	}
	public void setWebdict(String webdict) {
		this.webdict = webdict;
	}
	public Map<String, List<String>> getBasic() {
		return basic;
	}
	public void setBasic(Map<String, List<String>> basic) {
		this.basic = basic;
	}
	public String getL() {
		return l;
	}
	public void setL(String l) {
		this.l = l;
	}
	
}
