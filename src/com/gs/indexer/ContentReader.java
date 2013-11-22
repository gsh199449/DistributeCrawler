/**
 * GS
 */
package com.gs.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;

import org.apache.log4j.Logger;


/**
 * @author GaoShen
 * @packageName com.gs.io
 */
public class ContentReader {
	private Logger logger = Logger.getLogger(this.getClass());

	public String read(String path, long startoffset, long endoffset) {
		String content = null;
		File file = new File(path);
		try {
			FileInputStream fis = new FileInputStream(file);
			fis.skip(startoffset);
			byte b;
			int size = (int) (endoffset - startoffset);
			byte[] b1 = new byte[size + 999];// In order to avoid stackoverflow
			for (int i = 0; (b = (byte) fis.read()) != -1 && i < size; i++) {
				b1[i] = b;
			}
			content = new String(b1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return content;
	}

}
