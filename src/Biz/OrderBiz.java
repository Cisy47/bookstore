package Biz;

import Entity.Order;
import Entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 47 on 2016/6/6.
 */
public interface OrderBiz {
    public boolean createOrderFromShopCart(HashMap shopCart, User user) throws Exception;
    public Order[] getOrderByDate(Date startDate, Date endDate);
    List getOrderByUser(User user);
    Map<String, Integer> getTypeByUser(User user);
    Map<String, Integer> getTypeByDate(Date start,Date end);
    Map<String, Integer> getBookByDate(Date start,Date end);

}
