package com.gs.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DescriptionExtractorImpl implements DescriptionExtractor {

	@Override
	public String extractorFromHtml(String html) {
		String regex = "<meta name=\"description\" content=(.*?)/>|<meta name=\"Description\" content=(.*?)/>";
		Pattern pt = Pattern.compile(regex,Pattern.DOTALL);
		Matcher mt = pt.matcher(html);
		String re = "";
		if (mt.find()) {
			re = mt.group(1);
		}
		re = re.subSequence(1, (re.length()-2)).toString();
		return re;
	}

}
