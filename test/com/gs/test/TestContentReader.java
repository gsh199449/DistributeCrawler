package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.junit.Test;

import com.google.gson.Gson;
import com.gs.crawler.PagePOJO;
import com.gs.indexer.ContentReader;
import com.gs.indexer.Hit;

public class TestContentReader {

	@Test
	public void test() throws IOException {
		ContentReader cr = new ContentReader(new FileInputStream(new File("D://Test//testByte.txt")));
		while (cr.hasNext()) {
			System.out.println(cr.next());
		}
		System.out.println(ContentReader.read(new FileInputStream(new File("D://Test//testByte.txt")), 0));
		
	}
	
	@Test
	public void testJson(){
		Gson g = new Gson();
		String d = g.toJson(new PagePOJO("rrr",2,"yyy","t"));
		System.out.println(d);
	}

}
