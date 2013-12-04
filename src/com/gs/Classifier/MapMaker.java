package com.gs.Classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.wltea.analyzer.core.IKSegmenter;

public class MapMaker {
	public void make(String ditrectory) {
		File f = new File(ditrectory);
		for (File ff : f.listFiles()) {
			Map<String, Double> map = null;
			String[] extensions = { "txt", "TXT" };
			map = new HashMap<String, Double>();
			Iterator<File> iterateFiles = FileUtils.iterateFiles(ff,
					extensions, true);
			while (iterateFiles.hasNext()) {
				File current = iterateFiles.next();
				for (String key : extractSingle(current).keySet()) {
					double freq = (double) (map.get(key) == null ? 0
							: (double) map.get(key));
					map.put(key, freq == 0 ? 1 : freq + 1);
				}
			}
			try {
				if (new File(ff + "//map").exists())
					new File(ff + "//map").delete();
				FileOutputStream os = new FileOutputStream(ff + "//map");
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(map);
				oos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public Map<String, Integer> extractSingle(File current) {
		FileReader d = null;
		List<String> list = new LinkedList<String>();// sped aritical
		try {
			d = new FileReader(current);
			IKSegmenter ik = new IKSegmenter(d, true);
			while (true) {
				try {
					String a = ik.next().getLexemeText();
					if (!list.contains(a) && a.length() > 1
							&& a.matches(".*[\u4e00-\u9faf].*")) {
						list.add(a);
					}
				} catch (NullPointerException e) {
					break;
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Integer> m = new HashMap<String, Integer>();
		for (String word : list) {
			int freq = (Integer) m.get(word) == null ? 0 : (Integer) m
					.get(word);
			m.put(word, freq == 0 ? 1 : freq + 1);
		}
		return m;
	}
}
