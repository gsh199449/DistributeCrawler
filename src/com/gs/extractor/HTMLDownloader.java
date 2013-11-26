package com.gs.extractor;

import java.io.IOException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

public class HTMLDownloader {
	private Logger logger = Logger.getLogger(this.getClass());
	public String down(URL u){
		String s = null;
		HttpClient hc = new HttpClient();
		GetMethod get;
		try {
			hc.setConnectionTimeout(3000);
			get = new GetMethod(u.url);
			hc.executeMethod(get);
			s = get.getResponseBodyAsString();
			get.releaseConnection();
		} catch (ConnectTimeoutException e) {
			logger.warn(u.level+"连接超时");
		} catch (HttpException e) {
			logger.warn(u.url+"Http错误");
		} catch (IOException e) {
			logger.error("IO错误");
		}
		if(s == null || s.equals("")){
			return s;
		}
		return s;
	}
}
