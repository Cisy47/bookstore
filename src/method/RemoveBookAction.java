package method;

import Biz.BookBiz;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by 47 on 2016/6/11.
 */
public class RemoveBookAction extends ActionSupport{
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
        if (parameters.get("bookId") == null){
            json.put("status", "Error");
            json.put("message", "错误的参数!");
            content = json.toString();
            return SUCCESS;
        }
        int bookId = Integer.parseInt(((String[])parameters.get("bookId"))[0]);
        bookBiz.removeBook(bookId);
        json.put("status", "Success");
        json.put("message", "删除成功！");
        content = json.toString();
        return SUCCESS;
    }
}
