package functional.WebService.RESTful;

import Dao.BookDao;
import Entity.Book;
import Entity.Describe;
import functional.Lucene.LuceneUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;


/**
 * Created by 47 on 2017/5/5.
 */
@Path("/BookService")
@Component("bookServiceBean")
public class BookResource {

    @Resource(name="bookDao")
    private BookDao bookDao;

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    LuceneUtil lucene;

    @Context
    private UriInfo context;
    /** Creates a new instance of BookREST */
    public BookResource() { }

    @GET
    @Path("/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBook(@PathParam("bookId") int id)
    {
        JSONObject json = new JSONObject();
        Book b =bookDao.getBookById(id);
        json.put("book",b);
        return b;
    }

    @GET
    @Path("/bookDetail/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBookDetail(@PathParam(("bookId"))Integer id){
        Book b = bookDao.getBookById(id);
        Query query=new Query();
        Criteria criteria=Criteria.where("id").is(id);
        query.addCriteria(criteria);
        Describe d = mongoTemplate.findOne(query,Describe.class);
        if(d==null)
            d = new Describe(0," ");
        JSONObject object = new JSONObject();
        object.put("name",b.getName());
        object.put("author",b.getAuthor());
        object.put("publisher",b.getPublisher());
        object.put("price",b.getPrice());
        object.put("stock",b.getStock());
        object.put("desc",d.getDes());
        return object.toString();
    }

    @POST
    @Path("/searchBook")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String searchDetail(@QueryParam("desc") String desc){
        try {
            List<Describe> list = lucene.search(desc);
            JSONArray object = new JSONArray();
            for(Describe d:list){
                JSONObject js = new JSONObject();
                Book b = bookDao.getBookById(d.getId());
                js.put("name",b.getName());
                js.put("desc",d.getDes());
                object.add(js);
            }
            return object.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "Error!"+e.toString();
        }
    }

    @GET
    @Path("/list")
    @Produces("application/json")
    public Book[] getBooks(){
        return bookDao.getAllBook();
    }
}

