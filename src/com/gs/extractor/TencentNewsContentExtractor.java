/**
 * 
 */
package com.gs.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class TencentNewsContentExtractor implements ContentExtractor {

	/**
	 * @param html
	 * @return
	 */
	public String extractFromHtml(String html) {
		String re = "";
		if (html == null) {
			return re;
		}
		String regex = "<div id=\"Cnt-Main-Article-QQ\".*?>(.*?)</div>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		if (mt.find()) {
			re = (mt.group(1).replaceAll("[a-zA-Z_/\"<>=.:]", ""));
		}
		return re;
	}

	@Override
	public String extract(String url) {
		// TODO Auto-generated method stub
		return null;
	}
}
