package com.gs.crawler;

import java.util.LinkedList;
import java.util.List;

import com.gs.extractor.HTMLDownloader;
import com.gs.extractor.TecentNewsTitleExtrator;
import com.gs.extractor.TencentNewsContentExtractor;
import com.gs.extractor.TencentNewsLinkExtractor;
import com.gs.extractor.URL;

/**
 * @author gaoshen
 * 
 */
public class Crawler {
	
	/**
	 * 抓取队列
	 */
	public LinkedList<URL> q = new LinkedList<URL>();
	
	/**
	 * 此Crawler对应ID，也就是qq文件中对应连接的偏移量
	 */
	private String id;

	public Crawler(String id) {
		this.id = id;
	}

	public void addURL(URL u) {
		q.add(u);
	}

	/**
	 * @param seeds 需要抓取的连接
	 * @return 返回抓取的储存json的LinkedList
	 */
	public List<String> crawl(String seeds) {
		TencentNewsLinkExtractor le = new TencentNewsLinkExtractor(this, 2, 50);
		TencentNewsContentExtractor ce = new TencentNewsContentExtractor();
		TecentNewsTitleExtrator te = new TecentNewsTitleExtrator();
		HTMLDownloader d = new HTMLDownloader();
		
		LinkedList<String> rl = new LinkedList<String>();//储存结果的List
		q.add(new URL(seeds, 1));//将种子装入带抓取的队列
		int id = 0;// 每个页面的子ID号.
		while (!q.isEmpty()) {
			URL u = q.remove();//从队列里面拿出一个URL
			String html = d.down(u);//下载html
			if (html == null || html.equals(""))//判断html是否为空
				continue;
			LinkedList<URL> s = le.extractFromHtml(html, u.level);
			q.addAll(s);//循环抓取 //TODO 有问题！
			String content = ce.extractFromHtml(html);
			if (content == null || content.equals(""))
				continue;
			PagePOJO pojo = new PagePOJO();
			pojo.setId(Integer.parseInt(this.id + id++));
			pojo.setUrl(u.url);
			pojo.setContent(ce.extractFromHtml(html));
			pojo.setTitle(te.extractFromHtml(html));
			rl.add(pojo.toJson());
			pojo = null;
			System.out.println("Queue Size : " + q.size() + "Level : "
					+ u.level + "URL : " + u.url);
		}
		return rl;
	}
}
