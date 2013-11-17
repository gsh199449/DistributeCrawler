/**
 * GS
 */
package com.gs.extractor;

import java.util.List;



/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public interface LinkExtractor {
	public List<URL> extract(URL paurl, int topN);
	public String getHtml();
}