package com.gs.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://3g.163.com/news
 * @author GS
 *
 */
public class WangYiWapNewsContentExtractor implements ContentExtractor {

	@Override
	public String extractFromHtml(String html) {
		String regex = "<div class=\"content\">(.*?)</div>";
		Pattern pt = Pattern.compile(regex,Pattern.DOTALL);
		Matcher mt = pt.matcher(html);
		String re = "";
		while (mt.find()) {
			re += mt.group(0);
		}
		re = re.replaceAll("<.*?>", "");//抹掉所有尖括号的内容
		re = re.replaceAll("\\s", "");//抹掉所有空白
		return re;
	}

}
