package method;

import Biz.BookBiz;
import Entity.Book;
import Entity.ShopCartItem;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 47 on 2016/6/9.
 */
public class ShowCartAction extends ActionSupport {
    ArrayList<ShopCartItem> arrayList;
    HashMap<Integer,Integer> cartSearch;
    HashMap<Book,Integer> cart;
    private String content;
    private BookBiz bookBiz;

    public ArrayList<ShopCartItem> getArrayList() {
        return arrayList;
    }
    public void setArrayList(ArrayList<ShopCartItem> arrayList) {
        this.arrayList = arrayList;
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

    public HashMap<Integer, Integer> getCartSearch() {
        return cartSearch;
    }

    public void setCartSearch(HashMap<Integer, Integer> cartSearch) {
        this.cartSearch = cartSearch;
    }

    public HashMap<Book, Integer> getCart() {
        return cart;
    }

    public void setCart(HashMap<Book, Integer> cart) {
        this.cart = cart;
    }

    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        HashMap<Integer, Integer> hashMap;
        if (hSession.get("shopCart") == null ){
            content = "购物车为空";
            return "finish";
        }else{
            hashMap = (HashMap)hSession.get("shopCart");
        }
        if (hashMap.size() == 0){
            content = "购物车为空";
            return "finish";
        }
        arrayList = bookBiz.getBookDetail(hashMap);
        //return "continue";
        return "test";
    }

    public String showCart() throws Exception{
        ActionContext context = ActionContext.getContext();

        Map parameters = (Map)context.get("request");
        //JSONObject json = new JSONObject();
        if (cart==null || cart.size()==0 ) {
            content = "购物车为空";
            return "finish";
        }
        updateList();
        parameters.put("list",arrayList);
        context.getSession().put("cart",cartSearch);
        return SUCCESS;
    }

    public String addBook() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        JSONObject json = new JSONObject();
        if (parameters.get("bookId") == null){
            json.put("status", "Error");
            json.put("message", "参数为空！");
            content = json.toString();
            return SUCCESS;
        }
        int bookId = Integer.parseInt(((String[])parameters.get("bookId"))[0]);
        int bookNum;
        Book b = bookBiz.getBook(bookId);
        if(bookBiz==null){
            bookNum=0;
        }
        if (parameters.get("bookNum") == null){
            bookNum = 1;
        }else {
            bookNum = Integer.parseInt(((String[]) parameters.get("bookNum"))[0]);
        }
        if(cart==null){
            cart = new HashMap<Book, Integer>();
            cartSearch = new HashMap<Integer, Integer>();
        }
        if (cartSearch.containsKey(bookId)){
            int num = cart.get(bookId) + bookNum;
            if (num > 0) {
                cartSearch.put(bookId, num);
                cart.put(b,num);
            }
            else {
                cartSearch.remove(bookId);
                cart.remove(b);
            }
        }else{
            cartSearch.put(bookId, bookNum);
            cart.put(b,bookNum);
        }
        json.put("status", "Success");
        json.put("message", "添加成功！");
        setContent(json.toString());
        return SUCCESS;
    }

    private void updateList() {
        if(arrayList==null){
            arrayList=new ArrayList<ShopCartItem>();
        }
        arrayList.clear();
        ShopCartItem shopCartItem = new ShopCartItem();
        Iterator iter = cart.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Book bk= (Book) entry.getKey();
            int num = (Integer) entry.getValue();
            shopCartItem.setBook(bk);
            shopCartItem.setBookNum(num);
            arrayList.add(shopCartItem);
        }
    }
}
