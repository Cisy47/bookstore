package method;

import Biz.OrderBiz;
import Biz.UserBiz;
import Entity.Order;
import Entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 47 on 2016/6/9.
 */
public class OrderAction extends ActionSupport {
    private String content;
    List<Order> orderList;
    private OrderBiz orderBiz;
    private UserBiz userBiz;
    String payPass;

    public String getPayPass() {
        return payPass;
    }
    public void setPayPass(String payPass) {
        this.payPass = payPass;
    }
    public UserBiz getUserBiz() {
        return userBiz;
    }
    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public List<Order> getOrderList() {
        return orderList;
    }
    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public OrderBiz getOrderBiz() {
        return orderBiz;
    }

    public void setOrderBiz(OrderBiz orderBiz) {
        this.orderBiz = orderBiz;
    }

    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        User user = (User)hSession.get("user");
        orderList=orderBiz.getOrderByUser(user);
        if (orderList == null){
            content = "当前无订单!";
            return "finish";
        }
        if (orderList.size() == 0){
            content = "当前无订单!";
            return "finish";
        }
        Order[] orderArray = new Order[orderList.size()];
        orderList.toArray(orderArray);
        Arrays.sort(orderArray);
        orderList = Arrays.asList(orderArray);
        return "continue";
    }

    public String createOrder() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        User user = (User)hSession.get("user");
        if(user==null){
            HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
            String username = request.getUserPrincipal().getName();
            user=userBiz.getUserByAccount(username);
            hSession.put("user",user);
        }
        if (hSession.get("cart") == null){
            json.put("status", "Empty");
            json.put("message", "购物车为空！");
            content = json.toString();
            return ERROR;
        }
        HashMap shopCart = (HashMap)hSession.get("cart");
        if (shopCart.size() == 0){
            json.put("status", "Empty");
            json.put("message", "购物车为空！");
            content = json.toString();
            return ERROR;
        }
        userBiz.verUserPayPass(user.getUsername(),payPass);
        try{orderBiz.createOrderFromShopCart(shopCart, user);}
        catch (Exception e){
            if(e.getMessage().equals("余额不足")){
                json.put("status", "Error");
                json.put("message", "对不起, 您的余额不足,请充值");
                content = json.toString();
                return ERROR;
            }else if(e.getMessage().equals("库存不足")){
                json.put("status", "Error");
                json.put("message", "对不起, 书本货存不足, 无法下单!");
                content = json.toString();
                return ERROR;
            }
        }
        content = json.toString();
        shopCart.clear();
        return SUCCESS;
    }

}
