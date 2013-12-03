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


