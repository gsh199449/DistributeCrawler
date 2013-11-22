/**
 * GS
 */
package com.gs.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.gs.crawler.PagePOJO;


/**
 * @author GaoShen
 * @packageName com.gs.io
 */
public class ContentReader {
	private Logger logger = Logger.getLogger(this.getClass());

	public List<PagePOJO> read(String path) throws IOException {
		LinkedList<String> list = (LinkedList<String>) FileUtils.readLines(new File(path));
		Gson g = new Gson();
		LinkedList<PagePOJO> re = new LinkedList<PagePOJO>();
		while (!list.isEmpty()) {
			re.add(g.fromJson(list.remove(), PagePOJO.class));
		}
		return re;
	}

}
