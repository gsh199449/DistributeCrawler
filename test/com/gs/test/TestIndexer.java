package com.gs.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gs.indexer.Indexer;

public class TestIndexer {

	@Test
	public void test() {
		Indexer i = new Indexer();
		i.indexForCarrot2("D://Test//CarrotIndex", "D://Test//json");
	}

}
