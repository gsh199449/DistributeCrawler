package com.gs.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gs.indexer.Indexer;

public class TestIndexer {

	@Test
	public void test() {
		Indexer i = new Indexer();
		i.index("D://Test//index", "D://Test//json");
	}

}
