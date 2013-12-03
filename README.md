DistributeCrawler
=================
# 简介 #

这是一个基于Hadoop的分布式爬虫，目前只支持抓取腾讯新闻中心的新闻内容。支持插件机制，可以通过实现Extractor接口自己编写插件已实现对于各种网站的抓取和内容提取。

# 使用方法 #

1. 在com.gs.main.Test1中设置爬取时需要的各种参数

	```Java
	private static String dst = "hdfs://gs-pc:9000/home/test/qq.txt";//首页的链接暂存地
	private static String localSrc = "/home/gaoshen/qq.txt";//在master机器的暂存地
	private static int depth = 3;//深度
	private static int topN = 50;//每页抓取的最大链接数
	private static String outputPath = "hdfs://gs-pc:9000/home/test/output";//最终结果的输出路径
	private static String url = "http://news.qq.com";//需要抓取的地址
	private static String jobName = "DistributeCrawler";//Job的名称
	```

2.设置Redis里面的IP和端口
```Java
jj = new Jedis("localhost",8888);
```
3.然后就可以在Hadoop平台上运行了。
# 抓取流程 #
1. 首先先从`news.qq.com`上下载首页的所有连接。并存储到master本地，再写入HDFS。
2. 然后通过HDFS的本地计算将一个文件分发到各台Slaves上面。
3. 然后各台Slave机器调用map方法开始抓取。若未达到topN和depth限制则将本页面的连接加入到本机的待抓取队列中。再加入队列之前与Redis服务器进行通信，以确保该url未被其他机器抓取。
4. 每个页面通过`HttpClient`下载完成后，通过正则表达式进行过滤，只保留正文和标题，同时将这个页面封装为一个`PagePOJO`的POJO。这个POJO包含了正文，标题，URL，该页面的ID号（ID号是通过种子地址在种子文件中的偏移量也就是我们所谓的当前Slaves机器的ID号+一个叠加的计数器，即`ID=CrawlerID+counter`）。
5. 然后将这个POJO交给Json生成器来生成一个Json内容。
6. 最后通过Hadoop的`context`写入。
# 索引流程 #
1. 首先通过`JsonReader`一行一行的读出json内容（每一行是一个Json表达式）。`JsonReader`是通过`RandomAccessFil`e来实现的。因为他既可以满足从`InputStream`中按行读入的要求，还带有获取当前偏移量和skip方法，极为的好用。`JsonReader`返回一个`Hit`类型的封装。`Hit`封装了PagePOJO、文件名和在此文件中的起始偏移量。
2. 提取出每一个`Hi`t里面的正文、文件名和偏移量，并用`Lucene`索引。不储存content，但是储存文件名和起始偏移量。这样就可以摆脱对数据库的依赖。

