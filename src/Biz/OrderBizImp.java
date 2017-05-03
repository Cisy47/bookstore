package Biz;

import Dao.BookDao;
import Dao.OrderDao;
import Entity.Book;
import Entity.Order;
import Entity.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 47 on 2016/6/6.
 */
public class OrderBizImp implements OrderBiz {
    OrderDao orderDao;
    BookDao bookDao;
    BookBiz bookBiz;
    UserBiz userBiz;

    public UserBiz getUserBiz() {
        return userBiz;
    }
    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }
    public BookDao getBookDao() {
        return bookDao;
    }
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
    public OrderDao getOrderDao() {
        return orderDao;
    }
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
    public BookBiz getBookBiz() {
        return bookBiz;
    }
    public void setBookBiz(BookBiz bookBiz) {
        this.bookBiz = bookBiz;
    }

    public OrderBizImp(){}

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class} , propagation = Propagation.REQUIRED)
    public boolean createOrderFromShopCart(HashMap shopCart, User user) throws Exception{
        Order order = new Order();
        order.setCreateDate(new Date());
        int booknum=0;
        Iterator iter = shopCart.entrySet().iterator();
        int money = 0;
        HashMap<Book, Integer> hashMap = new HashMap();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            Book book = bookBiz.getBook((Integer)entry.getKey());
            int bookNum = (Integer) entry.getValue();
            booknum+=bookNum;
            bookBiz.addPopularity(book.getId());
            bookBiz.buyBook(book.getId(),bookNum);
            money = money + book.getPrice()*bookNum;
            hashMap.put(book, bookNum);
        }
        userBiz.decMoney(user.getId(),money);
        order.setPrice(money);
        order.setBooks(hashMap);
        order.setOwner(user);
        order.setBooknumber(booknum);
        orderDao.createOrder(order);
        return true;
    }

    public Order[] getOrderByDate(Date startDate, Date endDate){
        return orderDao.getOrderByDate(startDate, endDate);
    }

    public List getOrderByUser(User user){
        return orderDao.getOrderByUser(user);
    }

    public Map getTypeByUser(User user){
        return orderDao.getTypeByUser(user.getId());
    }

    public Map<String, Integer> getTypeByDate(Date start,Date end){
        return orderDao.getTypeByDate(start,end);
    }

    public Map<String, Integer> getBookByDate(Date start,Date end){
        return  orderDao.getBookByDate(start, end);
    }
}
