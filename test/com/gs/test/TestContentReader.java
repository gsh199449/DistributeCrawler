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
import com.gs.indexer.JsonReader;
import com.gs.indexer.Hit;

public class TestContentReader {

	@Test
	public void test() throws Exception {
		JsonReader cr = new JsonReader(new File("D://Test//20131116"));
		int i=0;
		while (cr.hasNext()) {
			System.out.println(cr.next());
			if(i<10){
				i++;
			}else break;
		}
		cr.close();
		
	}
	
	@Test
	public void testJson(){
		Gson g = new Gson();
		String d = g.toJson(new PagePOJO("rrr",2,"yyy","t"));
		System.out.println(d);
	}

}
