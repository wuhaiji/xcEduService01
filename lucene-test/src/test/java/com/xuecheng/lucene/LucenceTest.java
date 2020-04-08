package com.xuecheng.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.ArrayList;


/**
 * @author whj
 * @createTime 2020-04-08 21:55
 * @description
 **/
@SpringBootTest
public class LucenceTest {
    @Test
    public void testCase() throws Exception {
        //doubleFiled,FloatField、IntField、LongField、StringFild会建立索引，但不会分词，意味着查找是已定义匹配素有内容:如生日，年龄，价格，主键等
        //TestField 一定会说一个，一定会分词
        //StoredField 一定会存储，一定不会被索引，更不会分词

        //1 创建文档对象
        ArrayList<Document> docs = new ArrayList<>();
        Document document = new Document();
        // 创建并添加字段信息。参数：字段的名称、字段的值、是否存储，这里选Store.YES代表存储到文档列表。Store.NO代表不存储
        // 这里我们title字段需要用TextField，即创建索引又会被分词。StringField会创建索引，但是不会被分词
        document.add(new TextField("title", "谷歌地图之父跳槽facebook", Field.Store.YES));
        document.add(new TextField("content", "无", Field.Store.YES));
        document.add(new StringField("author", "张三", Field.Store.YES));
        docs.add(document);
        // 创建文档对象
        Document document2 = new Document();
        document2.add(new TextField("content", "无", Field.Store.YES));
        document2.add(new StringField("author", "张三", Field.Store.YES));
        document2.add(new TextField("title", "谷歌地图之父加盟FaceBook", Field.Store.YES));
        docs.add(document2);
        // 创建文档对象
        Document document3 = new Document();
        document3.add(new TextField("content", "无", Field.Store.YES));
        document3.add(new StringField("author", "张三", Field.Store.YES));
        document3.add(new TextField("title", "谷歌地图创始人拉斯离开谷歌加盟Facebook", Field.Store.YES));
        docs.add(document3);
        // 创建文档对象
        Document document4 = new Document();
        document4.add(new TextField("content", "无", Field.Store.YES));
        document4.add(new StringField("author", "张三", Field.Store.YES));
        document4.add(new TextField("title", "谷歌地图之父跳槽Facebook与Wave项目取消有关", Field.Store.YES));
        docs.add(document4);
        // 创建文档对象
        Document document5 = new Document();
        document5.add(new TextField("content", "无", Field.Store.YES));
        document5.add(new StringField("author", "张三", Field.Store.YES));
        document5.add(new TextField("title", "谷歌地图之父拉斯加盟社交网站Facebook", Field.Store.YES));
        docs.add(document5);
        // 创建文档对象
        Document document6 = new Document();
        document6.add(new TextField("title", "病毒", Field.Store.YES));
        document6.add(new StringField("author", "张三", Field.Store.YES));
        document6.add(new StringField("date", "2015-12-21 11:08", Field.Store.YES));

        StringBuilder content = new StringBuilder("");
        content.append("好几天没看书也没动笔了，一不读书就产生了很多浮念，挥之不去，大大的影响了生活质量。今天还好，在繁忙的工作之余，" +
                "偶然踏进了一个人的博客，里面有不少出彩且有趣的文章，像一个种植了很多奇花异草的百草园。我躲在这个园子里“拈花惹草”，直到雾" +
                "霾越来越重，像一团团散发着潮气的烂棉絮套子堆满窗户的时候，才如见到了赤练蛇般，从“花花草草”里跳了出来。");
        document6.add(new TextField("content", content.toString(), Field.Store.YES));
        docs.add(document6);

        //2 索引目录类,指定索引在硬盘中的位置
        FSDirectory directory = FSDirectory.open(new File("d:/indexDir"));
        //3 创建分词器对象
        IKAnalyzer ikAnalyzer = new IKAnalyzer();
        //4 索引写出工具 的配置对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, ikAnalyzer);
        //设置打开方式：OpenMode.APPEND会在原来的基础上追加新索引，OpenMode.CREATE会xIan清空原来的数据,在提交新的索引
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        //创建索引写出工具，参数：索引目录和配置
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //6 把文档交给IndexWriter
        indexWriter.addDocuments(docs);
        //7 提交
        indexWriter.commit();
        //8 关闭
        indexWriter.close();


    }

    @Test
    public void testSearch() throws Exception {
        // 索引目录对象
        Directory directory = FSDirectory.open(new File("d:/indexDir"));
        // 索引读取工具
        DirectoryReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        QueryParser parser = new QueryParser("content", new IKAnalyzer());
        // 创建查询对象
        Query query = parser.parse("花花草草");
        // 搜索数据,两个参数：查询条件对象要查询的最大结果条数
        // 返回的结果是 按照匹配度排名得分前N名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）。
        TopDocs topDocs = searcher.search(query, 5);
        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        // 取出文档编号
        System.out.println(
                "========================================================================================");
        for (ScoreDoc scoreDoc : scoreDocs) {
            //取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
            Document document = reader.document(docID);
            System.out.println("id: " + docID);
            System.out.println("title: " + document.get("title"));
            System.out.println("author: " + document.get("author"));
            System.out.println("content: " + document.get("content"));
            System.out.println("date: " + document.get("date"));
            // 取出文档得分
            System.out.println("得分： " + scoreDoc.score);
            System.out.println(
                    "========================================================================================");
        }

    }
}

