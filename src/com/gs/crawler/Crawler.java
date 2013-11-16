package com.gs.crawler;

import java.util.LinkedList;
import java.util.List;

import com.gs.extractor.HTMLDownloader;
import com.gs.extractor.TecentNewsTitleExtrator;
import com.gs.extractor.TencentNewsContentExtractor;
import com.gs.extractor.TencentNewsLinkExtractor;
import com.gs.extractor.URL;

public class Crawler {
	public LinkedList<URL> q = new LinkedList<URL>();
	public String id;
	public Crawler(String id) {
		this.id = id;
	}
	public void addURL(URL u){
		q.add(u);
	}
	public List<String> crawl(String seeds){
		TencentNewsLinkExtractor le = new TencentNewsLinkExtractor(this,2,30);
		TencentNewsContentExtractor ce = new TencentNewsContentExtractor();
		TecentNewsTitleExtrator te = new TecentNewsTitleExtrator();
		LinkedList<String> rl = new LinkedList<String>();
		q.add(new URL(seeds,1));
		HTMLDownloader d = new HTMLDownloader();
		int id = 0;
		while(!q.isEmpty()){
			URL u = q.remove();
			String html = d.down(u);
			if(html == null || html.equals("")) continue;
			LinkedList<URL> s = le.extractFromHtml(html,u.level);
			String content = ce.extractFromHtml(html);
			if(content == null || content.equals("")) continue;
			PagePOJO pojo = new PagePOJO();
			pojo.setId(Integer.parseInt(this.id+id++));
			pojo.setUrl(u.url);
			pojo.setContent(ce.extractFromHtml(html));
			pojo.setTitle(te.extractFromHtml(html));
			rl.add(pojo.toJson());
			pojo = null;
			System.out.println("Queue Size : "+q.size()+"Level : "+u.level+"URL : "+u.url);
		}
		return rl;
	}
}
