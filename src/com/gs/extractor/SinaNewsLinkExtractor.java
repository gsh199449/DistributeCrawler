package com.gs.extractor;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SinaNewsLinkExtractor implements LinkExtractor {

	private int deepth;
	private int topN;

	public SinaNewsLinkExtractor(int deepth, int topN) {
		this.topN = topN;
		this.deepth = deepth;
	}


	public LinkedList<URL> extractFromHtml(String html, final int level) {
		LinkedList<URL> s = new LinkedList<URL>();
		if (level >= deepth && deepth != 0){
			return s;// 若已经超过抓取深度则不再提取
		}
		String regex = "<a\\s.*?href=\"([^\"]+)\"[^>]*>(.*?)</a>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		int counter = 0;// 已抽取的连接计数器
		while (mt.find()) {
			String u = mt.group(1);
			if (u.startsWith("http://news.sina.com.cn/")
					&& (u.endsWith("htm") || u.endsWith("html") || u
							.endsWith("shtml"))) {
				URL re = new URL(u, level + 1);
				s.add(re);// 向结果List中添加
				counter++;
			}
			if (counter > topN && topN != 0)
				break;// 是否达到topN
		}
		return s;
	}


}
