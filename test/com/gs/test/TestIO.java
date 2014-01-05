package com.gs.test;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.gs.indexer.JsonReader;

public class TestIO {

	@Test
	public void test() {
		String content = null;
		File file = new File("D://Test//testByte.txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			//fis.skip(startoffset);
			byte b;
			//int size = (int) (10 - 1);
			byte[] b1 = new byte[999];// In order to avoid stackoverflow
			for (int i = 0; (b = (byte) fis.read()) != -1 && b != 125; i++) {
				b1[i] = b;
			}
			content = new String(b1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(content);
	}
	
	@Test
	public void testByte() throws Exception{
		JsonReader jr = new JsonReader(new File("D://Test//20131116"));
		int i=0;
		while(jr.hasNext()){
			long s = jr.next().getStartOffset();
			System.out.println(jr.read(new File("D://Test//20131116"), s));
			i++;
			if(i > 10)break;
		}
		jr.close();
	}
	
	@Test
	public void test1() throws IOException{
		JsonReader jr = new JsonReader(new File("D://Test//20131116"));
		System.out.println(jr.read(new File("D://Test//20131116"), 0));
		
	}

}
