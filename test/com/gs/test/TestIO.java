package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

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
	public void testByte(){
		byte b = '}';
		System.out.println(b);
	}

}
