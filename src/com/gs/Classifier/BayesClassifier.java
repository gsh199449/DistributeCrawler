package com.gs.Classifier;

import com.gs.Classifier.ChineseSpliter;
import com.gs.Classifier.ClassConditionalProbability;
import com.gs.Classifier.PriorProbability;
import com.gs.Classifier.TrainingDataManager;
import com.gs.Classifier.StopWordsHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
* 朴素贝叶斯分类器
*/
public class BayesClassifier 
{
	private Logger logger = Logger.getLogger(this.getClass());
	private static TrainingDataManager tdm;//训练集管理器
	//private String trainnigDataPath;//训练集路径
	private static double zoomFactor = 65.0f;
	private static BayesClassifier bayesClassifier = new BayesClassifier();
	/**
	* 默认的构造器，初始化训练集
	*/
	private BayesClassifier() 
	{
		tdm =TrainingDataManager.getInstance();
	}

	/**
	* 计算给定的文本属性向量X在给定的分类Cj中的类条件概率
	* <code>ClassConditionalProbability</code>连乘值
	* @param X 给定的文本属性向量
	* @param Cj 给定的类别
	* @return 分类条件概率连乘值，即<br>
	*/
	float calcProd(String[] X, String Cj) 
	{
		float ret = 1.0F;
		// 类条件概率连乘
		for (int i = 0; i <X.length; i++)
		{
			String Xi = X[i];
			//因为结果过小，因此在连乘之前放大10倍，这对最终结果并无影响，因为我们只是比较概率大小而已
			ret *=ClassConditionalProbability.calculatePxc(Xi, Cj)*zoomFactor;
		}
		// 再乘以先验概率
		ret *= PriorProbability.calculatePc(Cj);
		return ret;
	}
	/**
	* 去掉停用词
	* @param text 给定的文本
	* @return 去停用词后结果
	*/
	public String[] DropStopWords(String[] oldWords)
	{
		Vector<String> v1 = new Vector<String>();
		for(int i=0;i<oldWords.length;++i)
		{
			if(StopWordsHandler.IsStopWord(oldWords[i])==false)
			{//不是停用词
				v1.add(oldWords[i]);
			}
		}
		String[] newWords = new String[v1.size()];
		v1.toArray(newWords);
		return newWords;
	}
	/**
	* 对给定的文本进行分类
	* @param text 给定的文本
	* @return 分类结果
	*/
	public String classify(String text) 
	{
		String[] terms = null;
		terms= ChineseSpliter.split(text, " ").split(" ");//中文分词处理(分词后结果可能还包含有停用词）
		terms = DropStopWords(terms);//去掉停用词，以免影响分类
		
		String[] Classes = tdm.getTraningClassifications();//分类
		float probility = 0.0F;
		List<ClassifyResult> crs = new ArrayList<ClassifyResult>();//分类结果
		logger.info("classifying"+text.substring(0, 50));
		for (int i = 0; i <Classes.length; i++) 
		{
			String Ci = Classes[i];//第i个分类
			probility = calcProd(terms, Ci);//计算给定的文本属性向量terms在给定的分类Ci中的分类条件概率
			//保存分类结果
			ClassifyResult cr = new ClassifyResult();
			cr.classification = Ci;//分类
			cr.probility = probility;//关键字在分类的条件概率
			//System.out.println("In process....");
			logger.debug(Ci + "：" + probility);
			crs.add(cr);
		}
		//对最后概率结果进行排序
		java.util.Collections.sort(crs,new Comparator<ClassifyResult>() 
		{
			public int compare(final ClassifyResult o1,final ClassifyResult o2) 
			{
				final ClassifyResult m1 = (ClassifyResult) o1;
				final ClassifyResult m2 = (ClassifyResult) o2;
				final double ret = m1.probility - m2.probility;
				if (ret < 0) 
				{
					return 1;
				} 
				else 
				{
					return -1;
				}
			}
		});
		//返回概率最大的分类
		return crs.get(0).classification;
	}
	
	public static void main(String[] args)
	{
		String text = "韩国《朝鲜日报》16日报道称，韩国国防部改变说法是考虑到加入MD系统的争议。SM-3导弹可以在发生紧急情况时，对中国发射的远程导弹进入太空阶段在东海和西海上实施拦截，因此会刺激中国。也有人称，萨德系统的核心设备AN/TPY-2地面X-band雷达可能会严重刺激中国。这种雷达的探测距离达1000公里以上，如果部署在韩国，尤其是西海地区，就能提早探测到中国用于威慑美国的战略武器——洲际弹道导弹和潜射弹道导弹的发射动向。据悉，美国去年曾通过非正式渠道建议韩国政府在白翎岛部署X-band雷达，但韩国政府担心中国抗议予以拒绝。";
		BayesClassifier classifier = new BayesClassifier();//构造Bayes分类器
		String result = classifier.classify(text);//进行分类
		System.out.println("此项属于["+result+"]");
	}
	

	/**
	 * @return
	 */
	public static BayesClassifier getInstance() {
		return bayesClassifier;
	}
}