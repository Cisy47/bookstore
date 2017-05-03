package Dao;

import Entity.Order;
import Entity.User;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/* OrderDao 订单持久化操作类
 * */

public interface OrderDao {
	//生成订单
	boolean createOrderFromShopCart(HashMap shopCart, User user, JSONObject json);
	//查看一段时间内的订单
	public Order[] getOrderByDate(Date startDate, Date endDate);
	//添加Order
	public void addOrder(Order order);
	//查看用户的订单
	List getOrderByUser(User user);

	Map<String, Integer> getTypeByUser(int userId);

	Map<String, Integer> getTypeByDate(Date start,Date end);

	Map<String, Integer> getBookByDate(Date start,Date end);

	boolean createOrder(Order order);
}
