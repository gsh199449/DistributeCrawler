/**
 * GS
 */
package com.gs.Classifier;

import org.junit.Test;
/**
 * @author GaoShen
 * @packageName com.gs.Classifier
 */
public class ClassifierTest {

	@Test
	public void test() {
		BayesClassifier classifier = BayesClassifier.getInstance();
		long start = System.currentTimeMillis();
		String text = "沈丹阳说，受到直接刺激政策退出、高端和集团消费回落等因素的影响，今年以来消费市场虽然面临收入增长放缓境况，但是今年的消费也面临许多有利因素，全年总的形势前低后高，稳中有升。目前的消费增速应该说是比较正常，也比较健康。";
		String result = classifier.classify(text);//进行分类
		long use = System.currentTimeMillis() - start;
		System.out.println("use"+use+"ms");
		System.out.println("此项属于["+result+"]");
		 start = System.currentTimeMillis();
		 text = "丁俊晖本赛季一开始表现欠佳，不过在上海大师赛击败墨菲、罗伯逊等名将，并在决赛中国德比轻取肖国栋(微博) 夺冠，赢得职业生涯第七个排名赛冠军，打破主场排名赛八年无冠的魔咒。其后的球员巡回赛EPTC5又杀进决赛，可惜不敌马克-艾伦屈居亚军。此番印度赛，丁俊晖首轮4-3淘汰平奇斯，次轮4-3击败乔伊斯。丁俊晖和希金斯近5次交手赢了4次，但今年世界公开赛0-5惨败。";
		 result = classifier.classify(text);//进行分类
		 use = System.currentTimeMillis() - start;
		System.out.println("use"+use+"ms");
		System.out.println("此项属于["+result+"]");
	
	}
}
