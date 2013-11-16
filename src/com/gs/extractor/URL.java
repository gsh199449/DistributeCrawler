/**
 * 
 */
package com.gs.extractor;

import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.MyCrawler
 */
public class URL {
	private Logger logger = Logger.getLogger(this.getClass());
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
