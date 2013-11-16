package com.gs.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TecentNewsTitleExtrator {
	public String extractFromHtml(String html){
		String re = "";
		if(html == null){
			return re ;
		}
		String regex = "<title>(.*?)</title>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		if (mt.find()) {
			re = mt.group(1);
		}
		return re;
	}
}
