package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.gs.extractor.DescriptionExtractorImpl;

public class TestDescriptionExtractor {

	@Test
	public void test() throws IOException {
		DescriptionExtractorImpl e = new DescriptionExtractorImpl();
		System.out.println(e.extractorFromHtml(FileUtils.readFileToString(new File("D://Test//1.txt"),"gb2312")));
	}

}
