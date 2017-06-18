import com.opensymphony.xwork2.interceptor.annotations.Before;
import jeasy.analysis.MMAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;

/**
 * Created by siqi.lou on 13/06/2017.
 */
public class Test {
    private String ids[] = { "1", "2", "3" };
    private String citys[] = { "qingdao", "nanjing", "shanghai" };
    private String descs[] = { "Qingdao is a beautiful city.",
            "Nanjing is a city of culture.", "Shanghai is a bustling city." };

    private Directory dir;// 目录

    @Before
    public void setUp() throws Exception {
        dir = FSDirectory.open(Paths.get("D:\\lucene\\luceneIndex"));// 得到luceneIndex目录
        IndexWriter writer = getWriter();// 得到索引
        for (int i = 0; i < ids.length; i++) {
            Document doc = new Document();// 创建文档
            doc.add(new StringField("id", ids[i], Field.Store.YES));// 将id属性存入内存中
            doc.add(new StringField("city", citys[i], Field.Store.YES));
            doc.add(new TextField("desc", descs[i], Field.Store.NO));
            writer.addDocument(doc); // 添加文档
        }
        writer.close();
    }

    private IndexWriter getWriter() throws Exception {
        Analyzer analyzer = new MMAnalyzer(); // 标准分词器
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    public static void search(String indexDir, String q) throws Exception {
        Directory dir = FSDirectory.open(Paths.get(indexDir));// 打开目录
        IndexReader reader;// 进行读取
        reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);// 索引查询器
        Analyzer analyzer = new StandardAnalyzer(); // 标准分词器
        QueryParser parser = new QueryParser("contents", analyzer);// 在哪查询，第一个参数为查询的Document，在Indexer中创建了
        Query query = parser.parse(q);// 对字段进行解析后返回给查询
        long start = System.currentTimeMillis();
        TopDocs hits = is.search(query, 10);// 开始查询，10代表前10条数据；返回一个文档
        long end = System.currentTimeMillis();
        System.out.println("匹配 " + q + " ，总共花费" + (end - start) + "毫秒" + "查询到"
                + hits.totalHits + "个记录");
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);// 根据文档的标识获取文档
            System.out.println(doc.get("fullPath"));
        }
        reader.close();
    }

    public void main() {
        String indexDir = "D:\\lucene";
        String q = "ADD";
        try {
            search(indexDir, q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
