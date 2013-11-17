/**
 * GS
 */
package com.gs.crawler;

import com.google.gson.Gson;

/**
 * @author GaoShen
 * @packageName com.gs.utils
 */
public class PagePOJO {
	/**
	 * 
	 */
	public String url;
	public int id;
	public String title;
	public String content;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public PagePOJO() {

	}

	public PagePOJO(String json) {
		Gson gson = new Gson();
		gson.fromJson(json, PagePOJO.class);
	}

	/**
	 * @param startoffset
	 * @param endoffset
	 * @param url
	 * @param id
	 * @param mergepath
	 * @param content
	 */
	public PagePOJO(String url, int id, String content,String title) {
		this.url = url;
		this.id = id;
		this.content = content;
		this.title = title;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return
	 */
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	


}
