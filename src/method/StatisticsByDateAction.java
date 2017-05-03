package method;

import Biz.OrderBiz;
import Entity.Order;
import com.opensymphony.xwork2.ActionContext;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 47 on 2016/6/6.
 */
public class StatisticsByDateAction {
    private String content;
    private List<Order> orderList;
    private OrderBiz orderBiz;
    private List<Map.Entry<String,Integer>> mappingList;
    Map<String, Integer> typeBuy;

    public Map<String, Integer> getTypeBuy() {
        return typeBuy;
    }
    public void setTypeBuy(Map<String, Integer> typeBuy) {
        this.typeBuy = typeBuy;
    }
    public List<Map.Entry<String, Integer>> getMappingList() {
        return mappingList;
    }
    public void setMappingList(List<Map.Entry<String, Integer>> mappingList) {
        this.mappingList = mappingList;
    }
    public List<Order> getOrderList() {
        return orderList;
    }
    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
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
        if (parameters.get("startDate") == null||
                parameters.get("endDate") == null){
            json.put("status", "Error");
            json.put("message", "缺少参数！");
            content = json.toString();
            return "finish";
        }
        String startDateString = ((String[])parameters.get("startDate"))[0];
        String endDateString = ((String[])parameters.get("endDate"))[0];
        if (startDateString == "" || endDateString == ""){
            json.put("status", "Error");
            json.put("message", "参数为空！");
            content = json.toString();
            return "finish";
        }
        System.out.println(startDateString);
        System.out.println(endDateString);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);
        System.out.println(startDate.toString());
        System.out.println(endDate.toString());
        Order[] orders = orderBiz.getOrderByDate(startDate, endDate);
        Map<String, Integer> map = orderBiz.getBookByDate(startDate,endDate);
        orderList = Arrays.asList(orders);
//        Iterator iter = orderList.iterator();
        typeBuy=orderBiz.getTypeByDate(startDate,endDate);
//        while (iter.hasNext()){
//            Order order = (Order)iter.next();
//            Iterator iter2 = order.getBooks().entrySet().iterator();
//            while (iter2.hasNext()){
//                Map.Entry<Book, Integer> entry = (Map.Entry)iter2.next();
//                Book book=entry.getKey();
//                String name = entry.getKey().getName();
//                int number = entry.getValue();
//                if (map.get(name) != null){
//                    map.put(name, map.get(name) + number);
//                }else{
//                    map.put(name, number);
//                }
//            }
//        }
        mappingList = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        //通过比较器实现比较排序
        Collections.sort(mappingList, new Comparator<Map.Entry<String,Integer>>(){
            public int compare(Map.Entry<String,Integer> mapping1,Map.Entry<String,Integer> mapping2){
                return -mapping1.getValue().compareTo(mapping2.getValue());
            }
        });
        return "continue";
    }
}
