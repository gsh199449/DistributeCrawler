package com.gs.indexer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.document.Document;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.gs.Classifier.BayesClassifier;
import com.gs.crawler.PagePOJO;

/**
 * @author GaoShen
 * @packageName com.gs.Lucene
 */
public class Searcher {
	private Logger logger = Logger.getLogger(this.getClass());
	private String indexField;
	private String docDirectory;

	/**
	 * @param indexField
	 *            the indexfile
	 * @param queryString
	 *            the queryString
	 * @return list a list of url
	 */
	public Searcher(String docDirectory, String indexField) {
		this.docDirectory = docDirectory;
		this.indexField = indexField;
	}

	public LinkedList<Hit> search(String queryString, boolean classify,int topDocs) {
		LinkedList<Hit> hits = new LinkedList<Hit>();
		try {
			File path = new File(indexField);
			Directory directory = FSDirectory.open(path);
			IndexReader reader = IndexReader.open(directory);
			IndexSearcher seacher = new IndexSearcher(reader);
			QueryParser query = new QueryParser(Version.LUCENE_35, "content",
					new IKAnalyzer());
			Query q = query.parse(queryString);
			TopDocs td = seacher.search(q, topDocs);
			ScoreDoc[] sds = td.scoreDocs;
			for (ScoreDoc sd : sds) {
				Hit hit = new Hit();
				Document d = seacher.doc(sd.doc);
				hit.setFileName(d.get("filename"));
				hit.setStartOffset(Long.valueOf(d.get("startOffset")));
				PagePOJO pojo = JsonReader.read(new File(docDirectory
						+ File.separator + hit.getFileName()),
						hit.getStartOffset());
				if (pojo == null) {
					hit = null;
					pojo = null;
					continue;
				}
				hit.setPagePOJO(pojo);
				if (classify) {
					hit.setClazz(BayesClassifier.getInstance().classify(
							hit.getPagePOJO().getContent()));// 写入类别
				}
				hits.add(hit);
			}
			seacher.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return hits;
	}

}
