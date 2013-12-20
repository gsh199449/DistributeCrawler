package com.gs.Classifier;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
* @author GS 
* 
*/
public class StopWordsHandler 
{
	public static boolean IsStopWord(String word)
	{
		Set<String> set = new HashSet<String>();
		try {
			for (String e : FileUtils.readLines(new File("D://Test//ChineseStopwords.txt"))) {
				set.add(e);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(set.contains(word)) return true;
		return false;
	}
}
