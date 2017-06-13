package functional.WebService.RESTful;

import Dao.BookDao;
import Entity.Book;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * Created by 47 on 2017/5/5.
 */
@Path("/BookService")
@Component("bookServiceBean")
public class BookResource {

    @Resource(name="bookDao")
    private BookDao bookDao;

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
    @Path("/list")
    @Produces("application/json")
    public Book[] getBooks(){
        return bookDao.getAllBook();
    }
}

