package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.gs.indexer.Hit;
import com.gs.indexer.Searcher;

public class TestSearcher {

	@Test
	public void test() throws IOException {
		Searcher s = new Searcher("D://Test//json");
		LinkedList<Hit> list = s.search("的");
		for(Hit h : list){
			System.out.println(h.getPagePOJO().title);
			//FileUtils.writeStringToFile(new File("D://Test//result.txt"), h.toString());
		}
	}

}
