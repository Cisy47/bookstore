package method;

import Biz.BookBiz;
import Biz.UserBiz;
import Entity.Book;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.util.Map;

/**
 * Created by 47 on 2016/6/9.
 */
public class ShowAllBookAction extends ActionSupport{
    private Book[] bookArray;
    private BookBiz bookBiz;
    public String category;
    private UserBiz userBiz;

    public UserBiz getUserBiz() {
        return userBiz;
    }

    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }

    public BookBiz getBookBiz() {
        return bookBiz;
    }

    public void setBookBiz(BookBiz bookBiz) {
        this.bookBiz = bookBiz;
    }

    public Book[] getBookArray() {
        return bookArray;
    }

    public void setBookArray(Book[] bookArray) {
        this.bookArray = bookArray;
    }

    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        bookArray = bookBiz.getAllBook();

        return SUCCESS;
    }

    public String showCategoryBook(){
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        category = ((String[])parameters.get("category"))[0];
        /*Map<String, Object> hSession = context.getSession();
        User user = (User)hSession.get("user");
        if(user==null){
            HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
            String username = request.getUserPrincipal().getName();
            user=userBiz.getUserByAccount(username);
            hSession.put("user",user);
        }
        //BookCategoryPermission p = new BookCategoryPermission(String.valueOf(user.getRole()), category);
        CategoryPermission p = new CategoryPermission(String.valueOf(user.getRole()), category);
        SecurityManager manager = System.getSecurityManager();
        if (manager == null){
            manager =new SecurityManager();
            System.setSecurityManager(manager);
        }
        manager.checkPermission(p);
        System.setSecurityManager(null);*/
        bookArray = bookBiz.getAllBook(category);
        return SUCCESS;
    }
}
