package functional.WebService.RESTful;

import Dao.BookDao;
import Entity.Book;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Created by 47 on 2017/5/5.
 */
@Path("/Book")
public class BookResource {

    @Autowired
    private BookDao bookDao;
    @Context
    private UriInfo context;
    /** Creates a new instance of BookREST */
    public BookResource() { }

    @GET
    @Path("/{bookid}")
    @Produces("application/json")
    public Book getBook(@PathParam("bookid") int id)
    {
        return bookDao.getBookById(id);
    }

    @GET
    @Path("/list")
    @Produces("application/json")
    public Book[] getBooks(){
        return bookDao.getAllBook();
    }
}

