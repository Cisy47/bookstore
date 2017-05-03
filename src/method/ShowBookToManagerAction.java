package method;

import Biz.BookBiz;
import Entity.Book;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by 47 on 2016/6/11.
 */
public class ShowBookToManagerAction extends ActionSupport{
    private String content;
    Book[] bookArray;
    private BookBiz bookBiz;

    public Book[] getBookArray() {
        return bookArray;
    }
    public void setBookArray(Book[] bookArray) {
        this.bookArray = bookArray;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public BookBiz getBookBiz() {
        return bookBiz;
    }
    public void setBookBiz(BookBiz bookBiz) {
        this.bookBiz = bookBiz;
    }

    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        bookArray=bookBiz.getAllBook();
        return SUCCESS;
    }
}
