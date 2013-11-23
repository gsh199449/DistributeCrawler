/**
 * GS
 */
package com.gs.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Iterator;
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
	private FileInputStream fis;
	private long flag = 0;

	public static LinkedList<PagePOJO> readFileToPOJOs(String path)
			throws IOException {
		List<String> list = FileUtils.readLines(new File(path));
		Gson g = new Gson();
		LinkedList<PagePOJO> re = new LinkedList<PagePOJO>();
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			re.add(g.fromJson(it.next(), PagePOJO.class));
		}
		return re;
	}

	public static PagePOJO read(FileInputStream fis, long startoffset) {
		String json = null;
		try {
			fis.skip(startoffset);
			char[] b1 = new char[99999];// Buffer
			char b = 0;
			int i = 0;
			for (i = 0; b != '}'; i++) {
				b = (char) fis.read();
				b1[i] = b;
			}
			char[] b2 = new char[i];
			int j = 0;
			for (j = 0; j < i; j++) {
				b2[j] = b1[j];// 抹掉b1后边的0
			}
			json = new String(b2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		return gson.fromJson(json, PagePOJO.class);
	}

	public ContentReader(FileInputStream fis) {
		this.fis = fis;
	}

	public Hit next() {
		Hit hit = new Hit();
		String json = null;
		int i = 0;
		try {
			char[] b1 = new char[99999];// Buffer
			char b = 0;
			if (flag == 0) {//补上因为判断文件是否到头而错过的｛
				b1[0] = '{';
				i=1;
			}else{
				i=0;
			}
			for (; b != '}'; i++) {
				b = (char) fis.read();
				if(b == '\n') {i = i-1;continue;} //第二次开始每次都有一个换行符，丢弃。
				b1[i] = b;
			}
			char[] b2 = new char[i];
			int j = 0;
			for (j = 0; j < i; j++) {
				b2[j] = b1[j];// 抹掉b1后边的0
			}
			json = new String(b2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		PagePOJO pojo = gson.fromJson(json, PagePOJO.class);
		hit.setPagePOJO(pojo);
		if (flag != 0)
			hit.setStartOffset(flag + 1);
		else
			hit.setStartOffset(flag);// 第一行的起始偏移量是0
		flag = flag + i;
		return hit;
	}

	public boolean hasNext() throws IOException {
		if (fis.read() == -1)//等待回滚
			return false;
		else{
			return true;
		}
	}

}
