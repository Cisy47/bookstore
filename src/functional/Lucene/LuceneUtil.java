package functional.Lucene;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Entity.Describe;
import com.opensymphony.xwork2.interceptor.annotations.Before;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;

import jeasy.analysis.MMAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by siqi.lou on 13/06/2017.
 */
@Component
public class LuceneUtil {
    private Directory dir;

    @Resource
    MongoTemplate mongoTemplate;

    public void setUp() throws Exception {
        dir = FSDirectory.open(Paths.get("luceneIndex"));// 得到luceneIndex目录
        IndexWriter writer = getWriter();// 得到索引
        List<Describe> list  = mongoTemplate.findAll(Describe.class);
        for (int i = 0; i < list.size(); i++) {
            Document doc = new Document();// 创建文档
            Describe d = list.get(i);
            doc.add(new StringField("id", d.getIdStr(), Field.Store.YES));// 将id属性存入内存中
            doc.add(new TextField("desc", d.getDes(), Field.Store.YES));
            writer.addDocument(doc); // 添加文档
        }
        writer.close();
    }

    private IndexWriter getWriter() throws Exception {
        Analyzer analyzer = new SmartChineseAnalyzer(); // 标准分词器
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    public List<Describe> search(String q) throws Exception {
        String indexDir = "luceneIndex";
        //String q = "繁荣";
        //int num = 3;
        List<Describe> result = new ArrayList<>();
        Directory dir = FSDirectory.open(Paths.get(indexDir));// 打开目录
        IndexReader reader;// 进行读取
        reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);// 索引查询器
        Analyzer analyzer = new SmartChineseAnalyzer(); // 标准分词器
        QueryParser parser = new QueryParser("desc", analyzer);// 在哪查询，第一个参数为查询的Document，在Indexer中创建了
        Query query = parser.parse(q);// 对字段进行解析后返回给查询
        long start = System.currentTimeMillis();
        TopDocs hits = is.search(query, null,3);// 开始查询，10代表前10条数据；返回一个文档
        ScoreDoc[] hitDocs = hits.scoreDocs;

        // 关键字高亮显示的html标签，需要导入lucene-highlighter-xxx.jar
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));


        for (int i = 0; i < hits.totalHits; i++) {
            Document doc = is.doc(hitDocs[i].doc);
            // 内容增加高亮显示
            TokenStream tokenStream = analyzer.tokenStream("desc", new StringReader(doc.get("desc")));
            String content = highlighter.getBestFragment(tokenStream, doc.get("desc"));
            result.add(new Describe(Integer.parseInt(doc.get("id")),content));
            System.out.println(content);
        }

        long end = System.currentTimeMillis();
        System.out.println("匹配 " + q + " ，总共花费" + (end - start) + "毫秒" + "查询到"
                + hits.totalHits + "个记录");
        reader.close();
        return result;
    }
}
