package com.gs.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.Test;

import com.gs.crawler.PagePOJO;
import com.gs.indexer.ContentReader;

public class TestContentReader {

	@Test
	public void test() {
		ContentReader cr = new ContentReader();
		try {
			LinkedList<PagePOJO> l = cr.readFileToPOJOs("D:\\Workspaces\\GitHubRepository\\DistributeCrawler\\NewsJson\\20131116");
			int i=0;
			for(PagePOJO pojo : l){
				System.out.println(pojo);
				i++;
				if(i > 5)break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
