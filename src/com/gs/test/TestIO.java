package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.gs.extractor.HTMLDownloader;
import com.gs.extractor.TencentNewsLinkExtractor;
import com.gs.extractor.URL;

public class TestIO {

	@Test
	public void test() {
		try {
			TencentNewsLinkExtractor le = new TencentNewsLinkExtractor();
			String data = new String();
			for(URL u : le.extractFromHtml(new HTMLDownloader().down(new URL("http://news.qq.com",1)), 1)){
				data += (u.url+"\n");
			}
			FileUtils.writeStringToFile(new File("/home/gaoshen/qq.txt"), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
