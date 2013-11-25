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
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(s == null || s.equals("")){
			return s;
		}
		return s;
	}
}
