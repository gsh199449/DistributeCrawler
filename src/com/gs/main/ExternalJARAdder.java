package com.gs.main;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public final class ExternalJARAdder {
	private FileSystem fs;
	private Configuration conf;
	
	protected ExternalJARAdder(FileSystem fs, Configuration conf) {
		this.fs = fs;
		this.conf = conf;
	}
	public final void add(String jarPath) throws IOException{
		DistributedCache.addArchiveToClassPath(new Path(jarPath), conf,fs);
	}
}
