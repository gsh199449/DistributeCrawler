/**
 * GS
 */
package com.gs.extractor;

import java.util.LinkedList;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public interface LinkExtractor {
	public LinkedList<URL> extractFromHtml(String html, final int level);
}