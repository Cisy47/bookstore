package Dao;

import Entity.Book;
import Entity.Order;
import Entity.User;
import method.HibernateUtil;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;

import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by 47 on 2016/6/4.
 */
public class OrderDaoImp implements OrderDao{
    public boolean createOrderFromShopCart(HashMap shopCart, User user, JSONObject json){
        Order order = new Order();
        order.setCreateDate(new Date());
        int booknum=0;
        Iterator iter = shopCart.entrySet().iterator();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int money = 0;
        HashMap<Book, Integer> hashMap = new HashMap();
        while (iter.hasNext()){
            Entry entry = (Entry)iter.next();
            Book book = (Book)session.get(Book.class, (Integer)entry.getKey());
            int bookNum = (Integer) entry.getValue();
            booknum+=bookNum;
            if (book.getStock() < bookNum){
                session.getTransaction().commit();
                json.put("status", "Error");
                json.put("message", "对不起, "+book.getName()+"货存不足, 无法下单!");
                return false;
            }else{
                book.setStock(book.getStock() - bookNum);
            }
            money = money + book.getPrice()*bookNum;
            hashMap.put(book, bookNum);
        }
        if (money > user.getCoin()){
            session.getTransaction().commit();
            json.put("status", "Error");
            json.put("message", "对不起, 您的余额不足,请联系管理员!(需要"+money+",而您仅有"+user.getCoin()+")");
            return false;
        }else{
            user.setCoin(user.getCoin() - money);
        }

        iter = shopCart.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            Book book = (Book) session.get(Book.class, (Integer) entry.getKey());
            session.update(book);
        }
        order.setPrice(money);
        order.setBooks(hashMap);
        order.setOwner(user);
        order.setBooknumber(booknum);
        session.save(order);
        session.getTransaction().commit();
        json.put("status", "Success");
        json.put("message", "恭喜您下单成功!");
        return true;
    }

    public Order[] getOrderByDate(Date startDate, Date endDate) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "from Order where createDate <= ? and createDate >= ?";
        Query query = session.createQuery(hql);
        query.setParameter(0, endDate, StandardBasicTypes.DATE);
        query.setParameter(1, startDate,StandardBasicTypes.DATE);
        List<User> list = query.list();
        Order[] orders = new Order[list.size()];
        System.out.println(list.size());
        list.toArray(orders);
        session.getTransaction().commit();
        return orders;
    }

    public void addOrder(Order order) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();
    }

    public List getOrderByUser(User user){
        if(user==null){
            return null;
        }
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        boolean b=tx.isActive();
        if(!b) {
            tx=session.beginTransaction();
        }
        String hql = "from Order where owner = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0, user.getId(), StandardBasicTypes.INTEGER);
        List<Order> list = query.list();
        System.out.println(list.size());
        tx.commit();
        return list;
    }

    public Map<String, Integer> getTypeByUser(int userId){
        Session session=HibernateUtil.getSessionFactory().getCurrentSession();
        String sql = "call getTypeByUser(?)";
        session.beginTransaction();
        List<Object[]> list = session.createSQLQuery(sql).setParameter(0, userId).list();
        String typename=null;
        int num=0;
        Map<String,Integer> map=new HashMap();
        for(Object[] o:list)
        {
            int len = o.length;
            for(int i=0;i<len;i++)
            {
                if(i==0){
                    typename=(String)o[i];
                }else{
                    num=((BigInteger)o[i]).intValue();
                }

            }
            map.put(typename,num);
        }
        session.getTransaction().commit();
        return map;
    }

    public Map<String, Integer> getTypeByDate(Date start,Date end){
        Session session=HibernateUtil.getSessionFactory().getCurrentSession();
        String sql = "call getTypeNumByDate(?,?)";
        session.beginTransaction();
        Query query=session.createSQLQuery(sql);
        query.setParameter(0,start);
        query.setParameter(1,end);
        List<Object[]> list = query.list();
        String typename=null;
        int num=0;
        Map<String,Integer> map=new HashMap();
        for(Object[] o:list)
        {
            int len = o.length;
            for(int i=0;i<len;i++)
            {
                if(i==0){
                    typename=(String)o[i];
                }else{
                    num=((BigInteger)o[i]).intValue();
                }

            }
            map.put(typename,num);
        }
        session.getTransaction().commit();
        return map;
    }

    public Map<String, Integer> getBookByDate(Date start,Date end){
        Session session=HibernateUtil.getSessionFactory().getCurrentSession();
        String sql = "call getNumByBook(?,?)";
        session.beginTransaction();
        Query query=session.createSQLQuery(sql);
        query.setParameter(0,start);
        query.setParameter(1,end);
        List<Object[]> list = query.list();
        String typename=null;
        int num=0;
        Map<String,Integer> map=new HashMap();
        for(Object[] o:list)
        {
            int len = o.length;
            for(int i=0;i<len;i++)
            {
                if(i==0){
                    typename=(String)o[i];
                }else{
                    num=((BigInteger)o[i]).intValue();
                }

            }
            map.put(typename,num);
        }
        session.getTransaction().commit();
        return map;
    }

    public boolean createOrder(Order order){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();
        return true;
    }
}
