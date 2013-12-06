package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestRegex {

	@Test
	public void test() throws IOException {
		String regex = "<div class=\"Main clearfix\">(.*?)<div class=\"show_author\">";
		Pattern pt = Pattern.compile(regex,Pattern.DOTALL);
		String html = FileUtils.readFileToString(new File("D://Test//3.txt"),"gb2312");
		Matcher mt = pt.matcher(html);
		String re = null;
		if (mt.find()) {
			re = mt.group(1);
		}
		Pattern pt1 = Pattern.compile("<p>(.*?)</p>",Pattern.DOTALL);
		Matcher mt1 = pt1.matcher(re);
		re = "";
		while (mt1.find()) {
			re += mt1.group(1);
		}
		re = re.replaceAll("<.*?>", "");//抹掉所有尖括号的内容
		re = re.replaceAll("\\s", "");//抹掉所有空白
	}

}
