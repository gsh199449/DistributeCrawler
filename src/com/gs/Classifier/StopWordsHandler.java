package com.gs.Classifier;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
* 停用词处理器
* @author phinecos 
* 
*/
public class StopWordsHandler 
{
	//private static String stopWordsList[] ={"的", "我们","要","自己","之","将","“","”","，","（","）","后","应","到","某","后","个","是","位","新","一","两","在","中","或","有","更","好",""};//常用停用词
	public static boolean IsStopWord(String word)
	{
		/*for(int i=0;i<stopWordsList.length;++i)
		{
			if(word.equalsIgnoreCase(stopWordsList[i]))
				return true;
		}*/
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
