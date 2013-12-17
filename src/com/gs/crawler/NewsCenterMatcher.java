package com.gs.crawler;

public class NewsCenterMatcher {
	public static NewsCenter match(String url){
		if(url.startsWith("http://news.qq.com"))
			return NewsCenter.Tencent;
		else if(url.startsWith("http://news.sina.com.cn/"))
			return NewsCenter.Sina;
		else if(url.startsWith("http://3g.163.com/news"))
			return NewsCenter.WangYiWap;
		else return NewsCenter.Unknow;
	}
}
