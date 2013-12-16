package com.gs.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gs.extractor.TitleExtractor;

public class SinaNewsTitleExtractor implements TitleExtractor {
	@Override
	public String extractFromHtml(String html){
		String re = "";
		if(html == null){
			return re ;
		}
		String regex = "<title>(.+?)_新浪新闻</title>";//去掉最后的尾坠
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		if (mt.find()) {
			re = mt.group(1);
		}
		return re;
	}

}
