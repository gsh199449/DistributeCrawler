package com.gs.crawler;

import com.gs.extractor.URL;

import redis.clients.jedis.Jedis;

public class Redis {
	private static Jedis jj;
	private Redis(){
		jj = new  Jedis("localhost");  
	}
	private final static Redis INSTANCE = new Redis();
	public static Redis getInstance(){
		return INSTANCE;
	}
	public boolean hasFetched(URL u){
		return jj.exists(u.url);  
	}
	public void add(URL u){
		jj.set(u.url, String.valueOf(u.level));
	}
}
