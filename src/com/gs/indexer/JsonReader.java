package com.gs.indexer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.gs.crawler.PagePOJO;

public class JsonReader {
	
	public List<PagePOJO> readAll(String path){
		LinkedList<PagePOJO> list = new LinkedList<PagePOJO>();
		List<String> pojos = null;
		try {
			pojos = FileUtils.readLines(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		for(String json : pojos){
			if (json != null && !json.equals("")) {
				list.add(gson.fromJson(json, PagePOJO.class));
			}
		}
		return list;
	}
	
	public PagePOJO readSingle(String path,int offset){
		PagePOJO result = new PagePOJO();
		
		return result;
	}
}
