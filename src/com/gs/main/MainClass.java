package com.gs.main;

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
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Progressable;

import com.gs.crawler.Crawler;
import com.gs.extractor.HTMLDownloader;
import com.gs.extractor.TencentNewsLinkExtractor;
import com.gs.extractor.URL;

/**
 * @author gaoshen
 *	Crawler运行主类，包括Mapper和Main
 *	我们认为url.txt中的连接为0深度，所设置的deepth为页面level。
 */
public class MainClass {
	private static String dst = "hdfs://gs-pc:9000/home/test/qq.txt";//首页的链接暂存地
    private static String localSrc = "/home/gaoshen/qq.txt";//在master机器的暂存地
    private static int depth = 3;//深度
    private static int topN = 80;//每页抓取的最大链接数
    private static String outputPath = "hdfs://gs-pc:9000/home/test/output";//最终结果的输出路径
    private static String url = "http://news.qq.com";//需要抓取的地址
    private static String jobName = "DistributeCrawler";//Job的名称
	/**
	 * @author gaoshen
	 *	Mapper类
	 */
	public static class CrawlMapper extends
			Mapper<LongWritable, Text, NullWritable, Text> {
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String r = "";
			Crawler c = new Crawler(key.toString(),value.toString(), topN,depth);// 以Input文件的行偏移量作为crawler的id
			System.out.println(key.toString() + "\t" + value.toString());// 打印此map获得的连接以及在文件中的偏移量
			for (String s : c.start()) {
				r += s + "\r";// 向context中写入Json
			}
			System.out.println(r);// 打印此map函数抓取的json内容
			if (!r.equals("")) {
				context.write(NullWritable.get(), new Text(r));
			}
		}
	}
	

	public static void main(String[] args) throws Exception {
	/*	try {
			TencentNewsLinkExtractor le = new TencentNewsLinkExtractor(2,100);
			String data = new String();
			new HTMLDownloader();
			for (URL u : le
					.extractFromHtml(HTMLDownloader.down(new URL(
							url, 1)), 1)) {
				data += (u.url+"\n");
			}
			FileUtils.writeStringToFile(new File(localSrc), data);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		// 先写到本地，然后再向hdfs写入。//TODO 拖裤子放屁
		Configuration conf = new Configuration();

/*		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		OutputStream out = fs.create(new Path(dst), new Progressable() {
			public void progress() {
				System.out.print(".");
			}
		});
		IOUtils.copyBytes(in, out, 4096, true);// 4096为buffersize
*/		// 以上内容为抽取news.qq.com首页的链接，然后生成qq.txt文件，从而实现分布式抓取news.qq.com
		Job job = new Job(conf, jobName);
		job.setJarByClass(MainClass.class);
		
		job.setMapperClass(CrawlMapper.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(dst));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
