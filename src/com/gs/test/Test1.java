package com.gs.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Progressable;

import com.gs.crawler.Crawler;
import com.gs.extractor.HTMLDownloader;
import com.gs.extractor.TencentNewsLinkExtractor;
import com.gs.extractor.URL;

public class Test1 {

	public static class TokenizerMapper extends
			Mapper<LongWritable, Text, NullWritable, Text> {

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String r = new String();
			Crawler c = new Crawler(key.toString());//以Input文件的行偏移量作为crawler的id
			System.out.println(key.toString() + "\t" + value.toString());
			for (String s : c.crawl(value.toString())) {
				if (s == null || s.equals(""))
					continue;
				r += s;
				r += "\r";
			}
			System.out.println(r);
			word.set(r);
			context.write(NullWritable.get(), word);
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			TencentNewsLinkExtractor le = new TencentNewsLinkExtractor();
			String data = new String();
			for(URL u : le.extractFromHtml(new HTMLDownloader().down(new URL("http://news.qq.com",1)), 1)){
				data += (u.url+"\n");
			}
			FileUtils.writeStringToFile(new File("/home/gaoshen/qq.txt"), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Configuration conf = new Configuration();

		String localSrc = "/home/gaoshen/qq.txt";
		String dst = "hdfs://gs-pc:9000/home/test/qq.txt";
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		OutputStream out = fs.create(new Path(dst), new Progressable() {
			public void progress() {
				System.out.print(".");
			}
		});
		IOUtils.copyBytes(in, out, 4096, true);

//		String[] otherArgs = new GenericOptionsParser(conf, args)
//				.getRemainingArgs();
//		if (otherArgs.length != 2) {
//			System.err.println("Usage: DsCrawler <in> <out>");
//			System.exit(2);
//		}
		Job job = new Job(conf, "DsCrawler");

		job.setJarByClass(Test1.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(dst));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://gs-pc:9000/home/test/output"));
		job.submit();
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
