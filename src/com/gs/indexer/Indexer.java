package com.gs.indexer;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @author GaoShen
 * @packageName com.gs.Lucene
 */
public class Indexer {
	private Logger logger = Logger.getLogger(this.getClass());
	private String indexField;
	private String docsField;
	private String encoding = "GB2312";

	/**
	 * @param indexField
	 *            the path to save index file
	 * @param docsField
	 *            the path to take the txts which want to be indexed
	 */
	public void index(String indexField, String docsField) {
		try {
			this.indexField = indexField;
			this.docsField = docsField;
			Directory directory = FSDirectory.open(new File(indexField));
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35,
					new IKAnalyzer());
			IndexWriter writer = new IndexWriter(directory, conf);
			File f = new File(docsField);
			Document doc;
			for (File file : f.listFiles()) {
				logger.info("Indexing   " + file.getName());
				doc = new Document();
				doc.add(new Field("content", new FileUtils().readFileToString(
						file, encoding), Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("offset", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				writer.addDocument(doc);
			}
			writer.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

}
