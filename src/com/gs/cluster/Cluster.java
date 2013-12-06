package com.gs.cluster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm;
import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.clustering.stc.STCClusteringAlgorithm;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.IDocumentSource;
import org.carrot2.core.LanguageCode;
import org.carrot2.core.ProcessingResult;
import org.carrot2.core.attribute.CommonAttributesDescriptor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gs.crawler.PagePOJO;
import com.gs.indexer.Hit;
import com.gs.indexer.JsonReader;

/**
 * 聚类器,提供两种聚类方式
 * 
 * @author GS
 */
public class Cluster {
	private Map<String,List<String>> result = new HashMap<String,List<String>>();
	/**
	 * 对所有的PagePOJO进行聚类
	 * 
	 * @author GS
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public ProcessingResult cluster(String docPath) throws IOException,
			Exception {
		@SuppressWarnings("unchecked")
		final Controller controller = ControllerFactory
				.createCachingPooling(IDocumentSource.class);
		final List<Document> documents = Lists.newArrayList();
		JsonReader jr = new JsonReader(new File(docPath));
		while (jr.hasNext()) {
			Hit h = jr.next();
			documents.add(new Document(h.getPagePOJO().getTitle(), h
					.getPagePOJO().getContent()));
		}
		jr.close();
		final Map<String, Object> attributes = Maps.newHashMap();
		CommonAttributesDescriptor.attributeBuilder(attributes).documents(
				documents);
		final ProcessingResult englishResult = controller.process(attributes,
				LingoClusteringAlgorithm.class);
		ConsoleFormatter.displayResults(englishResult);// 展示
		return englishResult;
	}

	/**
	 * 对指定的PagePOJO进行聚类
	 * 
	 * @author GS
	 * @param list
	 *            PagePOJO List
	 * @return ProcessingResult类,调用需要的方法即可.
	 * @throws IOException
	 * @throws Exception
	 */
	public Map<String,List<String>> cluster(List<PagePOJO> list) throws IOException,
			Exception {
		@SuppressWarnings("unchecked")
		final Controller controller = ControllerFactory
				.createCachingPooling(IDocumentSource.class);
		final List<Document> documents = Lists.newArrayList();
		Iterator<PagePOJO> it = list.iterator();
		while (it.hasNext()) {
			PagePOJO pojo = it.next();
			documents.add(new Document(pojo.getTitle(), pojo.getContent()));
		}
		final Map<String, Object> attributes = Maps.newHashMap();
		CommonAttributesDescriptor.attributeBuilder(attributes).documents(
				documents);
		final ProcessingResult englishResult = controller.process(attributes,
				LingoClusteringAlgorithm.class);
		ConsoleFormatter.displayResults(englishResult);// 展示
		for (org.carrot2.core.Cluster c : englishResult.getClusters()) {
			LinkedList<String> value = new LinkedList<String>(); 
			for (Document d : c.getAllDocuments()) {
				value.add(d.getField(Document.TITLE).toString());
			}
			result.put(c.getLabel(), value);
		}
		return result;
	}

}
