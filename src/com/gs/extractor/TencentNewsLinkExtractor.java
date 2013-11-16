/**
 * GS
 */
package com.gs.extractor;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.gs.crawler.Crawler;
import com.gs.crawler.Redis;


/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class TencentNewsLinkExtractor implements LinkExtractor {
	private Logger logger = Logger.getLogger(this.getClass());
	String url;
	private String html;
	private Crawler c;
	private int deepth;
	private int topN;
	
	public TencentNewsLinkExtractor(Crawler c,int deepth,int topN){
		this.topN = topN;
		this.deepth = deepth;
		this.c = c;
	}
	
	public TencentNewsLinkExtractor(){
	}
	

	public LinkedList<URL> extractFromHtml(String html,int level) {
		LinkedList<URL> s = new LinkedList<URL>();
		if(level >= deepth && deepth != 0) return s;
		String regex = "<a\\s.*?href=\"([^\"]+)\"[^>]*>(.*?)</a>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		int counter=0;
		Redis r = Redis.getInstance();
		while (mt.find()) {
			String u = mt.group(1);
			if (u.startsWith("http://news.qq.com")
					&& (u.endsWith("htm") || u.endsWith("html") || u
							.endsWith("shtml"))) {
				
					URL re = new URL(u, level + 1);
					if(r.hasFetched(re)) continue;
					r.add(re);//添加到Redis数据库中
					if (c != null) {
						c.addURL(re);
					}
					s.add(re);
					counter++;
			}
			if(counter > topN && topN != 0) break;
		}
		return s;
	}


	@Override
	public List<URL> extract(URL paurl, int topN) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getHtml() {
		// TODO Auto-generated method stub
		return null;
	}

}
