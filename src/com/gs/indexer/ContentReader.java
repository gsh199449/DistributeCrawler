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
	public static LinkedList<PagePOJO> readFileToPOJOs(String path) throws IOException {
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
			byte[] b1 = new byte[99999];// Buffer
			byte b;
			for (int i = 0; (b = (byte) fis.read()) != -1 && b != '}'; i++) {
				b1[i] = b;
			}
			json = new String(b1)+'}';
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		return gson.fromJson(json, PagePOJO.class);
	}
	
	public ContentReader(FileInputStream fis){
		this.fis = fis;
	}
	public Hit next(){
		Hit hit = new Hit();
		String json = null;
		int i = 0;
		try {
			fis.skip(flag);
			byte[] b1 = new byte[99999];// Buffer
			byte b;
			for (i = 0; (b = (byte) fis.read()) != -1 && b != '}'; i++) {
				b1[i] = b;
			}
			b1[i] = '}';
			json = new String(b1);
			System.out.println(json);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		PagePOJO pojo = gson.fromJson(json, PagePOJO.class);//FIXME
		hit.setPagePOJO(pojo);
		hit.setStartOffset(flag);
		flag = flag + i;
		return hit;
	}


}
