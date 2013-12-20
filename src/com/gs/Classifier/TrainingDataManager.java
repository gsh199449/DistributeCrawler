package com.gs.Classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
* 训练集管理器
*/

public class TrainingDataManager 
{
	private String[] traningFileClassifications;//训练语料分类集合
	private File traningTextDir;//训练语料存放目录
	//private static String defaultPath = "D:\\Lucene\\docs\\训练分类用文本\\";
	//private static String defaultPath = "D:\\Lucene\\ClassFile\\";
	//private static String defaultPath = "D:\\Lucene\\分类训练与测试语料库\\DRAP提供的测试训练语料\\TanCorpMinTrain\\";
	//private static String defaultPath = "D:\\Lucene\\分类训练与测试语料库\\复旦李荣陆提供的语料库\\";
	private static String defaultPath = "D:\\Lucene\\Corpus\\";
	//private static String defaultPath = "/opt/Test/Corpus";
	//private static String defaultPath = "D:\\Lucene\\分类训练与测试语料库\\DRAP提供的测试训练语料\\Corpus\\";
	private Map<String,Map<String,Double>> classMap = new HashMap<String,Map<String,Double>>();
	private static TrainingDataManager ini = new TrainingDataManager();
	@SuppressWarnings("unchecked")
	private TrainingDataManager() 
	{
		traningTextDir = new File(defaultPath);
		if (!traningTextDir.isDirectory()) 
		{
			throw new IllegalArgumentException("训练语料库搜索失败！ [" +defaultPath + "]");
		}
		this.traningFileClassifications = traningTextDir.list();
		//加载所有Map
		String ss[] = traningTextDir.list();
		for(int i= 0;i<ss.length;i++){
			Map<String, Double> map = new HashMap<String, Double>();
			ObjectInputStream ois = null;
			try {
				FileInputStream is = new FileInputStream(defaultPath+ss[i]+"\\map");
				ois = new ObjectInputStream(is);
				map = (Map<String, Double>) ois.readObject();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			classMap.put(ss[i], map);
			map = null;
		}
	}
	
	public static TrainingDataManager getInstance(){
		return ini;
	}
	/**
	* 返回训练文本类别，这个类别就是目录名
	* @return 训练文本类别
	*/
	public String[] getTraningClassifications() 
	{
		return this.traningFileClassifications;
	}
	/**
	* 根据训练文本类别返回这个类别下的所有训练文本路径（full path）
	* @param classification 给定的分类
	* @return 给定分类下所有文件的路径（full path）
	*/
	public String[] getFilesPath(String classification) 
	{
		File classDir = new File(traningTextDir.getPath() +File.separator +classification);
		String[] ret = classDir.list();
		for (int i = 0; i < ret.length; i++) 
		{
			ret[i] = traningTextDir.getPath() +File.separator +classification +File.separator +ret[i];
		}
		return ret;
	}

	/**
	* 返回给定路径的文本文件内容
	* @param filePath 给定的文本文件路径
	* @return 文本内容
	* @throws java.io.FileNotFoundException
	* @throws java.io.IOException
	*/
	public static String getText(String filePath) throws FileNotFoundException,IOException 
	{
	
		InputStreamReader isReader =new InputStreamReader(new FileInputStream(filePath),"GBK");
		BufferedReader reader = new BufferedReader(isReader);
		String aline;
		StringBuilder sb = new StringBuilder();
	
		while ((aline = reader.readLine()) != null)
		{
			sb.append(aline + " ");
		}
		isReader.close();
		reader.close();
		return sb.toString();
	}

	/**
	* 返回训练文本集中所有的文本数目
	* @return 训练文本集中所有的文本数目
	*/
	public int getTrainingFileCount()
	{
		int ret = 0;
		for (int i = 0; i < traningFileClassifications.length; i++)
		{
			ret +=getTrainingFileCountOfClassification(traningFileClassifications[i]);
		}
		return ret;
	}

	/**
	* 返回训练文本集中在给定分类下的训练文本数目
	* @param classification 给定的分类
	* @return 训练文本集中在给定分类下的训练文本数目
	*/
	public int getTrainingFileCountOfClassification(String classification)
	{
		File classDir = new File(traningTextDir.getPath() +File.separator +classification);
		return classDir.list().length;
	}

	/**
	* 返回给定分类中包含关键字／词的训练文本的数目
	* @param classification 给定的分类
	* @param key 给定的关键字／词
	* @return 给定分类中包含关键字／词的训练文本的数目
	*/
	public int getCountContainKeyOfClassification(String classification,String key) 
	{
		int ret = 0;
		ret = (int) (classMap.get(classification).containsKey(key) ? classMap.get(classification).get(key):0);
		return ret;
	}
}