package method;

import Biz.OrderBiz;
import Entity.Order;
import Entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 47 on 2016/6/9.
 */
public class PersonalStatisticAction extends ActionSupport {
    private String content;
    Map<String, Integer[]> mapDay;
    Map<String, Integer[]> mapMonth;
    Map<String, Integer[]> mapYear;
    Map<String, Integer> typeBuy;
    private OrderBiz orderBiz;

    public Map<String, Integer[]> getMapDay() {
        return mapDay;
    }
    public void setMapDay(Map<String, Integer[]> mapDay) {
        this.mapDay = mapDay;
    }
    public Map<String, Integer[]> getMapMonth() {
        return mapMonth;
    }
    public void setMapMonth(Map<String, Integer[]> mapMonth) {
        this.mapMonth = mapMonth;
    }
    public Map<String, Integer[]> getMapYear() {
        return mapYear;
    }
    public void setMapYear(Map<String, Integer[]> mapYear) {
        this.mapYear = mapYear;
    }
    public Map<String, Integer> getTypeBuy() {
        return typeBuy;
    }
    public void setTypeBuy(Map<String, Integer> typeBuy) {
        this.typeBuy = typeBuy;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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
        User user = (User)hSession.get("user");
        Iterator iter = orderBiz.getOrderByUser(user).iterator();
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        mapDay = new HashMap();
        mapMonth = new HashMap();
        mapYear = new HashMap();
//        typeBuy = new HashMap();
        typeBuy=orderBiz.getTypeByUser(user);
        while (iter.hasNext()){
            Order order = (Order)iter.next();
            String day = sdfDay.format(order.getCreateDate());
            if (mapDay.containsKey(day)){
                Integer[] intArr = mapDay.get(day);
                intArr[0] += order.getBooknumber();
                intArr[1] += 1;
                mapDay.put(day, intArr);
            }else{
                Integer[] intArr = new Integer[2];
                intArr[0] = order.getBooknumber();
                intArr[1] = 1;
                mapDay.put(day, intArr);
            }
            String month = sdfMonth.format(order.getCreateDate());
            if (mapMonth.containsKey(month)){
                Integer[] intArr = mapMonth.get(month);
                intArr[0] += order.getBooknumber();
                intArr[1] += 1;
                mapMonth.put(month, intArr);
            }else{
                Integer[] intArr = new Integer[2];
                intArr[0] = order.getBooknumber();
                intArr[1] = 1;
                mapMonth.put(month, intArr);
            }
            String year = sdfYear.format(order.getCreateDate());
            if (mapYear.containsKey(year)){
                Integer[] intArr = mapYear.get(year);
                intArr[0] += order.getBooknumber();
                intArr[1] += 1;
                mapYear.put(year, intArr);
            }else{
                Integer[] intArr = new Integer[2];
                intArr[0] = order.getBooknumber();
                intArr[1] = 1;
                mapYear.put(year, intArr);
            }
        }
        return "continue";
    }
}
