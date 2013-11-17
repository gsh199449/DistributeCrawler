/**
 * 
 */
package com.gs.extractor;


/**
 * @author GaoShen
 * @packageName com.gs.MyCrawler
 */
public class URL {
	public String url;
	public int level;

	/**
	 * 
	 */
	public URL(String url, int level) {
		this.level = level;
		this.url = url;
	}

	public URL() {
	}
}
