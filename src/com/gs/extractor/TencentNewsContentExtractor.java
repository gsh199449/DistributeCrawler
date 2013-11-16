/**
 * 
 */
package com.gs.extractor;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.parserapplications.StringExtractor;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class TencentNewsContentExtractor implements ContentExtractor {
	private Logger logger = Logger.getLogger(TencentNewsContentExtractor.class);

	/**
	 * @param url
	 *            which want to be extract the content
	 * @return the main content of the webpage
	 */
	@Deprecated
	public String extractWithHtmlParser(String url) {
		String re = null;
		try {
			Parser parser;
			NodeFilter filter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "Cnt-Main-Article-QQ"));
			NodeList list;
			parser = new Parser(url);
			list = parser.extractAllNodesThatMatch(filter);
			if(list.size() == 0){
				return re;
			}
			for (int i = 0; i < list.size(); i++) {
				NodeList chirdrenList = list.elementAt(i).getChildren();
				NodeFilter childrenfilter = new TagNameFilter("p");
				NodeList plist = chirdrenList.extractAllNodesThatMatch(childrenfilter);
				for (int j = 0; j < plist.size(); j++) {
					try {
						re = (plist.elementAt(j).getFirstChild().getText());
					} catch (NullPointerException e) {
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return re;
	}

	/* (non-Javadoc)
	 * @see com.gs.extractor.ContentExtractor#extract(java.lang.String)
	 */
	@Override
	public String extract(String url) {
		String re =null;
		HttpClient hc = new HttpClient();
		GetMethod get;
		String s = null;
		try {
			hc.setConnectionTimeout(5000);
			get = new GetMethod(url);
			hc.executeMethod(get);
			s = (get.getResponseBodyAsString());
			get.releaseConnection();
		} catch (ConnectTimeoutException e) {
			logger.error("Bad connection" + url);
			return re;
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		
		if(s == null){
			return re;
		}
		String regex = "<div id=\"Cnt-Main-Article-QQ\".*?>(.*?)</div>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(s);
		if (mt.find()) {
			re = (mt.group(1).replaceAll("[a-zA-Z_/\"<>=.:]", ""));
		}
		return re;
	}

	/**
	 * @param html
	 * @return
	 */
	public String extractFromHtml(String html) {
		String re = "";
		if(html == null){
			return re ;
		}
		String regex = "<div id=\"Cnt-Main-Article-QQ\".*?>(.*?)</div>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		if (mt.find()) {
			re = (mt.group(1).replaceAll("[a-zA-Z_/\"<>=.:]", ""));
		}
		return re;
	}
}
 
