package com.gs.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 缺省的正文抽取器,仅仅是抽取html的中文
 * @author GS
 *
 */
public class DefaultContentExtractor implements ContentExtractor {

	@Override
	public String extractFromHtml(String html) {
		Pattern pt1 = Pattern.compile("[\u4e00-\u9fa5]*",Pattern.DOTALL);
		Matcher mt1 = pt1.matcher(html);
		String re = "";
		while (mt1.find()) {
			re += mt1.group(0);
		}
		return re;
	}

}
