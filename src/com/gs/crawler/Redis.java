package com.gs.crawler;

import org.apache.log4j.Logger;

import com.gs.extractor.URL;

import redis.clients.jedis.Jedis;

public class Redis {
	private Logger logger = Logger.getLogger(this.getClass());
	private static Jedis jj;

	private Redis() {
		try {
			jj = new Jedis("localhost");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private final static Redis INSTANCE = new Redis();

	public static Redis getInstance() {
		return INSTANCE;
	}

	public boolean hasFetched(URL u) {
		return jj.exists(u.url);
	}

	public void add(URL u) {
		jj.set(u.url, String.valueOf(u.level));
	}
}
