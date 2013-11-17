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
	
	private int deepth;
	
	/**
	 * 此Crawler对应ID，也就是qq文件中对应连接的偏移量
	 */
	private String id;

	/**
	 * 抓取队列
	 */
	public LinkedList<URL> queue = new LinkedList<URL>();

	private int topN;

	/**
	 * @param id
	 * 设置Crawler的ID
	 */
	public Crawler(String id,int topN,int deepth) {
		this.id = id;
		this.topN = topN;
		this.deepth = deepth;
	}

	/**
	 * @param u
	 * 添加URL到此Crawler的抓取队列中
	 */
	public void addURL(URL u) {
		queue.add(u);
	}

	/**
	 * @param seeds 需要抓取的连接
	 * @return 返回抓取的储存json的LinkedList
	 */
	public List<String> crawl(String seeds) {
		TencentNewsLinkExtractor le = new TencentNewsLinkExtractor(deepth-1, topN);
		TencentNewsContentExtractor ce = new TencentNewsContentExtractor();
		TecentNewsTitleExtrator te = new TecentNewsTitleExtrator();
		HTMLDownloader downloader = new HTMLDownloader();
		
		LinkedList<String> resultList = new LinkedList<String>();//储存结果的List
		queue.add(new URL(seeds, 1));//将种子装入带抓取的队列
		int id = 0;// 每个页面的子ID号.
		while (!queue.isEmpty()) {
			Redis r = Redis.getInstance();//Redis为单例模式
			URL u = queue.remove();//从队列里面拿出一个URL
			if(r.hasFetched(u)){
				continue;
			}
			r.add(u);//向Redis里添加URL
			String html = downloader.down(u);//下载html
			if (html == null || html.equals(""))//判断html是否为空
				continue;
			for (URL url : le.extractFromHtml(html, u.level)) {//提取本页面的连接
				if (r.hasFetched(url))//通过Redis判断是否已经抓取
					continue;//若已经抓取则不再向待抓取队列中添加
				addURL(url);//添加到Crawler的队列中
			}
			String content = ce.extractFromHtml(html);//抽取本页面的内容
			if (content == null || content.equals(""))//判断内容是否为空,即是否为新闻内容页面
				continue;
			PagePOJO pojo = new PagePOJO();
			pojo.setId(Integer.parseInt(this.id + id++));//设置pageid
			pojo.setUrl(u.url);
			pojo.setContent(content);
			pojo.setTitle(te.extractFromHtml(html));
			resultList.add(pojo.toJson());//添加到结果List中
			pojo = null;//置空pojo
			System.out.println("Queue Size : " + queue.size() + "Level : "
					+ u.level + "URL : " + u.url);//打印当前Queue状态
		}
		return resultList;
	}
}
