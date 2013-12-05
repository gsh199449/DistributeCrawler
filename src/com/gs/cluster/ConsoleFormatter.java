
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2013, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package com.gs.cluster;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.carrot2.core.Cluster;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;
import org.carrot2.core.attribute.CommonAttributesDescriptor;

/**
 * Simple console formatter for dumping {@link ProcessingResult}.
 */
public class ConsoleFormatter
{
    /**
     * 对processingResult进行全面的展示,输出至控制台.
     * @author GS
     * @param processingResult
     */
    public static void displayResults(ProcessingResult processingResult)
    {
        final Collection<Document> documents = processingResult.getDocuments();//所有的文档
        final Collection<Cluster> clusters = processingResult.getClusters();//所有的类别
        final Map<String, Object> attributes = processingResult.getAttributes();//参数

        // Show documents
        if (documents != null)
        {
            displayDocuments(documents);//打印所有文档
        }

        // Show clusters
        if (clusters != null)
        {
            displayClusters(clusters);//打印所有分类
        }

        // Show attributes other attributes
        displayAttributes(attributes);//打印参数
    }

    /**
     * 显示Collection里面的每一个文档,显示标题和URL
     * @author GS
     * @param documents
     */
    public static void displayDocuments(final Collection<Document> documents)
    {
        System.out.println("Collected " + documents.size() + " documents\n");//所有的文档总数
        for (final Document document : documents)
        {
            displayDocument(0, document);//显示单个文档,包括显示标题和URL
        }
    }

    /**
     * 显示此聚类结果的参数,输出至控制台.
     * @author GS
     * @param attributes
     */
    public static void displayAttributes(final Map<String, Object> attributes)
    {
        System.out.println("Attributes:");

        String DOCUMENTS_ATTRIBUTE = CommonAttributesDescriptor.Keys.DOCUMENTS;
        String CLUSTERS_ATTRIBUTE = CommonAttributesDescriptor.Keys.CLUSTERS;
        for (final Map.Entry<String, Object> attribute : attributes.entrySet())
        {
            if (!DOCUMENTS_ATTRIBUTE.equals(attribute.getKey())
                && !CLUSTERS_ATTRIBUTE.equals(attribute.getKey()))
            {
                System.out.println(attribute.getKey() + ":   " + attribute.getValue());
            }
        }
    }

    public static void displayClusters(final Collection<Cluster> clusters)
    {
        displayClusters(clusters, Integer.MAX_VALUE);
    }

    public static void displayClusters(final Collection<Cluster> clusters,
        int maxNumberOfDocumentsToShow)
    {
        displayClusters(clusters, maxNumberOfDocumentsToShow,
            ClusterDetailsFormatter.INSTANCE);
    }

    public static void displayClusters(final Collection<Cluster> clusters,
        int maxNumberOfDocumentsToShow, ClusterDetailsFormatter clusterDetailsFormatter)
    {
        System.out.println("\n\nCreated " + clusters.size() + " clusters\n");//显示聚类数
        int clusterNumber = 1;
        for (final Cluster cluster : clusters)
        {
            displayCluster(0, "" + clusterNumber++, cluster, maxNumberOfDocumentsToShow,
                clusterDetailsFormatter);
        }
    }

    /**
     * 展示单个文档
     * @author GS
     * @param level
     * @param document
     */
    private static void displayDocument(final int level, Document document)//展示每一个文档
    {
        final String indent = getIndent(level);

        System.out.printf(indent + "[%2s] ", document.getStringId());//打印文档ID号
        System.out.println(document.getField(Document.TITLE));//打印标题
        final String url = document.getField(Document.CONTENT_URL);//正文URL
        if (StringUtils.isNotBlank(url))//如果document里面带有正文的URL则打印
        {
            System.out.println(indent + "     " + url);
        }
        System.out.println();
    }

    /**
     * 对一个类进行展示.
     * @author GS
     * @param level
     * @param tag
     * @param cluster
     * @param maxNumberOfDocumentsToShow
     * @param clusterDetailsFormatter
     */
    private static void displayCluster(final int level, String tag, Cluster cluster,
        int maxNumberOfDocumentsToShow, ClusterDetailsFormatter clusterDetailsFormatter)
    {
        final String label = cluster.getLabel();//当前类的标题

        // indent up to level and display this cluster's description phrase
        for (int i = 0; i < level; i++)
        {
            System.out.print("  ");
        }
        System.out.println(label + "  "
            + clusterDetailsFormatter.formatClusterDetails(cluster));

        // if this cluster has documents, display three topmost documents.
        int documentsShown = 0;
        for (final Document document : cluster.getDocuments())
        {
            if (documentsShown >= maxNumberOfDocumentsToShow)//如果达到最大展示数的话不再展示
            {
                break;
            }
            displayDocument(level + 1, document);//这个level是干嘛的?
            documentsShown++;//当前分类已经展示的文档数
        }
        if (maxNumberOfDocumentsToShow > 0
            && (cluster.getDocuments().size() > documentsShown))
        {
            System.out.println(getIndent(level + 1) + "... and "
                + (cluster.getDocuments().size() - documentsShown) + " more\n");
        }

        // finally, if this cluster has subclusters, descend into recursion.
        final int num = 1;
        for (final Cluster subcluster : cluster.getSubclusters())
        {
            displayCluster(level + 1, tag + "." + num, subcluster,
                maxNumberOfDocumentsToShow, clusterDetailsFormatter);
        }
    }

    private static String getIndent(final int level)
    {
        final StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++)
        {
            indent.append("  ");
        }

        return indent.toString();
    }

    /**
     * 聚类详细参数的格式化器
     * @author GS
     */
    public static class ClusterDetailsFormatter
    {
        public final static ClusterDetailsFormatter INSTANCE = new ClusterDetailsFormatter();

        protected NumberFormat numberFormat;

        public ClusterDetailsFormatter()
        {
            numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
        }

        public String formatClusterDetails(Cluster cluster)//显示当前分类的详细参数,包括文档数和打分
        {
            final Double score = cluster.getScore();
            return "(" + cluster.getAllDocuments().size() + " docs"
                + (score != null ? ", score: " + numberFormat.format(score) : "") + ")";
        }
    }
}
