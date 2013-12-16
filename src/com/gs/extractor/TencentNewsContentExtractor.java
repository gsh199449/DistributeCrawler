/**
 * 
 */
package com.gs.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class TencentNewsContentExtractor implements ContentExtractor {

	/**
	 * @param html
	 * @return
	 */
	@Override
	public String extractFromHtml(String html) {
		String re = new String();
		if (html == null) {
			return re;
		}
		String regex = "<div id=\"Cnt-Main-Article-QQ\".*?>(.*?)</P>.?</div>";//正文最后是</p>换行</div>或者</P></div>为结束标志的
		Pattern pt = Pattern.compile(regex,Pattern.DOTALL);
		Matcher mt = pt.matcher(html);//s为网页的html内容
		if (mt.find()) {
			re = mt.group(1);
		}
		Pattern pt1 = Pattern.compile("<script>.*?</script>",Pattern.DOTALL);
		Matcher mt1 = pt1.matcher(re);
		re = mt1.replaceAll("");
		Pattern pt2 = Pattern.compile("<style>.*?</style>",Pattern.DOTALL);
		Matcher mt2 = pt2.matcher(re);
		re = mt2.replaceAll("");
/*		Pattern pt3 = Pattern.compile("<!--[if !IE]>.*?<![endif]-->",Pattern.DOTALL);
		Matcher mt3 = pt3.matcher(re);
		re = mt3.replaceAll("");*///FIXME 这个标签不知道为什么抹不掉
		re = re.replaceAll("<.*?>", "");//抹掉所有尖括号的内容
		re = re.replaceAll("\\s", "");//抹掉所有空白
		re = re.replaceAll("正在播放", "");//停用词
		re = re.replaceAll("资料图", "");//停用词
		return re;
	}

}
