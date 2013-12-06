/**
 * GS
 */
package com.gs.extractor;


/**
 * Extract the main text of an url
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public interface ContentExtractor {
	public String extractFromHtml(String Html);
	
}