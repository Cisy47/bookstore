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

public class BookAction extends ActionSupport {
    private String content;
    private BookBiz bookBiz;

    public BookBiz getBookBiz() {
        return bookBiz;
    }
    public void setBookBiz(BookBiz bookBiz) {
        this.bookBiz = bookBiz;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        if (parameters.get("name") == null ||
                parameters.get("author") == null ||
                parameters.get("publisher") == null ||
                parameters.get("price") == null ||
                parameters.get("stock") == null ||
                parameters.get("type") == null){
            json.put("status","error");
            json.put("warning","信息不完整！");
            content = json.toString();
            return SUCCESS;
        }
        if (((String[])parameters.get("name"))[0] == "" ||
                ((String[])parameters.get("author"))[0] == "" ||
                ((String[])parameters.get("publisher"))[0] == "" ||
                ((String[])parameters.get("price"))[0] == "" ||
                ((String[])parameters.get("stock"))[0] == "" ||
                ((String[])parameters.get("type"))[0] == ""){
            json.put("status","error");
            json.put("warning","信息不完整！");
            content = json.toString();
            return SUCCESS;
        }
        String name = ((String[])parameters.get("name"))[0];
        String author = ((String[])parameters.get("author"))[0];
        String publisher = ((String[])parameters.get("publisher"))[0];
        String type = (((String[])parameters.get("type"))[0]);
        int price = Integer.parseInt(((String[])parameters.get("price"))[0]);
        int stock = Integer.parseInt(((String[])parameters.get("stock"))[0]);
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPrice(price);
        book.setStock(stock);
        book.setType(type);
        bookBiz.addBook(book);
        json.put("status", "success");
        json.put("message", "添加成功!");
        content = json.toString();
        return SUCCESS;
    }

    public String bookDetail() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        if (parameters.get("bookId") == null){
            json.put("status", "Error");
            json.put("message", "参数为空！");
            content = json.toString();
            return SUCCESS;
        }
        int bookId = Integer.parseInt(((String[])parameters.get("bookId"))[0]);
        Book book=bookBiz.getBook(bookId);
        json.put("status", "Success");
        json.put("message", "添加成功！");
        json.put("bookname",book.getName());
        json.put("author",book.getAuthor());
        json.put("publisher",book.getPublisher());
        json.put("price",book.getPrice());
        json.put("stock",book.getStock());
        json.put("type",book.getType());
        setContent(json.toString());
        return SUCCESS;
    }

    public String modifyBook() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        if (parameters.get("bookId") == null){
            json.put("status", "Error");
            json.put("status", "错误的参数!");
            content = json.toString();
            return SUCCESS;
        }
        bookBiz.modifyBook(parameters);
        json.put("status", "Success");
        json.put("message", "更改成功！");
        content = json.toString();

        return SUCCESS;
    }
}
