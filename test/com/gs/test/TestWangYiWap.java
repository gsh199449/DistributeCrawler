package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.gs.extractor.WangYiWapNewsContentExtractor;

public class TestWangYiWap {

	@Test
	public void test() throws IOException {
		WangYiWapNewsContentExtractor e = new WangYiWapNewsContentExtractor();
		System.out.println(e.extractFromHtml(FileUtils.readFileToString(new File("D://Test//123.txt"),"gb2312")));
	}

}
