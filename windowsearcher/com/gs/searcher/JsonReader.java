/**
 * GS
 */
package com.gs.searcher;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gs.searcher.PagePOJO;

/**
 * 读取分布式爬虫处理好的Json文件
 * 
 * @author GaoShen
 * @packageName com.gs.io
 */
public class JsonReader implements Closeable {
	private static Logger logger = Logger.getLogger(JsonReader.class);
	private File file;

	private RandomAccessFile rf;

	/**
	 * 读出指定偏移量的PagePOJO
	 * 
	 * @param file
	 * @param startoffset
	 * @return
	 * @throws FileNotFoundException
	 */
	public static PagePOJO read(File file, long startoffset)
			throws FileNotFoundException, IOException {
		String json = null;
		RandomAccessFile r = new RandomAccessFile(file, "r");
		r.seek(startoffset);
		json = new String(r.readLine().getBytes("iso8859-1"), "utf-8");
		Gson gson = new Gson();
		try {
			r.close();
			return gson.fromJson(json, PagePOJO.class);
		} catch (JsonSyntaxException e) {
			logger.warn(json + "格式错误");
			return null;
		}
		
	}

	/**
	 * 用commons IO包里面的ReadLines方法实现，当问价过大的时候内存会溢出
	 * 将这个文件里的所有json格式的内容制成PagePOJO格式，然后封装在LinkedList中返回
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public static LinkedList<PagePOJO> readFileToPOJOs(String path)
			throws IOException {
		List<String> list = null;
		try {
			list = FileUtils.readLines(new File(path));
		} catch (OutOfMemoryError e) {
			logger.fatal(e.getMessage());
		}
		Gson g = new Gson();
		LinkedList<PagePOJO> re = new LinkedList<PagePOJO>();
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			re.add(g.fromJson(it.next(), PagePOJO.class));
		}
		return re;
	}

	/**
	 * 如果用到next方法必须调用此构造函数来初始化File
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public JsonReader(final File file) throws FileNotFoundException {
		this.rf = new RandomAccessFile(file, "r");
		this.file = file;
	}

	public void close() throws IOException {
		try {
			this.rf.close();
		} catch (Exception e) {
			logger.error("rf关闭失败" + e.getMessage());
		}
	}

	/**
	 * 调用next方法之前,查询是否还有下一个Json内容
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean hasNext() throws IOException {
		long p = rf.getFilePointer();
		if (rf.read() == -1) {
			rf.seek(p);
			return false;
		} else {
			rf.seek(p);
			return true;
		}
	}

	/**
	 * 读取下一个Json，前提是已经初始化了FileinputStream
	 * 
	 * @return
	 * @throws Exception
	 */
	public Hit next() throws Exception {
		if (rf == null)
			throw new Exception(
					"RandomAccessFile未初始化，调用JsonReader(File)来初始化RandomAccessFile");
		long offset = rf.getFilePointer();
		Hit hit = new Hit();
		String json = null;
		json = new String(rf.readLine().getBytes("iso8859-1"), "utf-8");
		Gson gson = new Gson();
		PagePOJO pojo;
		try {
			pojo = gson.fromJson(json, PagePOJO.class);
		} catch (JsonSyntaxException e) {
			logger.warn(json + "格式错误");
			return null;
		}
		hit.setPagePOJO(pojo);
		hit.setFileName(this.file.getName());
		hit.setStartOffset(offset);
		return hit;
	}


}
