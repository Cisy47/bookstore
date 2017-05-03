package Dao;

import Entity.Book;
import method.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 47 on 2016/6/4.
 */
public class BookDaoImp extends HibernateDaoSupport implements BookDao{
    private SessionFactory sessionFactory;


    public boolean addBook(Book book) {
        //更新Jedis
        Jedis jedis = new Jedis("localhost",6379);
        jedis.set(String.valueOf(book.getId()),book.toString());

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        return true;
    }

    public Book[] getAllBook() {
        Jedis jedis = new Jedis("localhost",6379);
        Set<String> set =jedis.keys("*");
        if(set.isEmpty()) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.getTransaction();
            boolean b = tx.isActive();
            if (!b) {
                tx.begin();
            }
            Query query = session.createQuery("from Book");
            List list = query.list();
            Book[] bookArray = new Book[list.size()];
            list.toArray(bookArray);
            tx.commit();
            for(int i=0;i<bookArray.length;i=i+1){
                Book book=bookArray[i];
                jedis.set(String.valueOf(book.getId()),book.toString());
            }
            return bookArray;
        }
        else{
            Book[] bookArray = new Book[set.size()];
            int i=0;
            for (String str : set) {
                Book b=fromString(jedis.get(str));
                bookArray[i]=b;
                i=i+1;
            }
            return bookArray;
        }
    }

    public Book[] getAllBook(String category){
        Jedis jedis = new Jedis("localhost",6379);
        Set<String> set =jedis.keys("*");
        if(set.isEmpty()) {
           fillJedis(jedis);
        }
        set=jedis.keys("*");
        ArrayList<Book> books=new ArrayList<Book>();
        for (String str : set) {
            if (jedis.get(str).contains("type@" + category + "@")) {
                Book b = fromString(jedis.get(str));
                books.add(b);
            }
        }
        Book[] result= new Book[books.size()];
        books.toArray(result);
        return result;
        /*
        else {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.getTransaction();
            boolean b = tx.isActive();
            if (!b) {
                tx.begin();
            }
            Query query = session.createQuery("from Book where type=?");
            query.setParameter(0, category);
            List list = query.list();
            Book[] bookArray = new Book[list.size()];
            list.toArray(bookArray);
            tx.commit();
            return bookArray;
        }*/
    }

    public Book[] searchBook(String content) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "from Book where name like ? or publisher like ? or author like ?";
        Query query = session.createQuery(hql);
        query.setParameter(0, '%' + content + '%', StandardBasicTypes.STRING);
        query.setParameter(1, '%' + content + '%', StandardBasicTypes.STRING);
        query.setParameter(2, '%' + content + '%', StandardBasicTypes.STRING);
        List list = query.list();
        Book[] bookArray = new Book[list.size()];
        list.toArray(bookArray);
        session.getTransaction().commit();
        return bookArray;
    }

    public void removeBook(int bookId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Book book = (Book) session.get(Book.class, bookId);
        if (session.get(Book.class,bookId) == null) {
            return;
        }
        session.delete(book);
        session.getTransaction().commit();
        //更新Jedis
        Jedis jedis = new Jedis("localhost",6379);
        jedis.del(String.valueOf(bookId));
    }

    public Book getBookById(int bookId) {
        Jedis jedis = new Jedis("localhost",6379);
        if(jedis.keys("*").isEmpty()){
            fillJedis(jedis);
        }
        if(jedis.exists(String.valueOf(bookId))){
            String s =jedis.get(String.valueOf(bookId));
            Book b=fromString(s);
            return b;
        }else {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            String hql = "from Book where id=?";
            org.hibernate.Query query = session.createQuery(hql);
            query.setParameter(0, bookId, StandardBasicTypes.INTEGER);
            List<Book> books = query.list();
            session.getTransaction().commit();
            if (books != null && books.size() != 0) {
                Book b=books.get(0);
                jedis.set(String.valueOf(b.getId()),b.toString());
                return b;
            } else {
                return null;
            }
        }
    }

    public void updateBook(Book book) {
        //更新Jedis
        Jedis jedis = new Jedis("localhost",6379);
        jedis.set(String.valueOf(book.getId()),book.toString());

        String sql = "from Book as book where book.id=?";
        List<Book> result=(List<Book>) this.getHibernateTemplate().find(sql,book.getId());
        Book temp=result.get(0);
        temp.setName(book.getName());
        temp.setAuthor(book.getAuthor());
        temp.setType(book.getType());
        temp.setPopularity(book.getPopularity());
        temp.setPrice(book.getPrice());
        temp.setStock(book.getStock());
        temp.setPublisher(book.getPublisher());
        this.getHibernateTemplate().update(temp);
    }

    private Book fromString(String s){
        String[] paras=s.split("@");
        Book b = new Book();
        b.setId(Integer.parseInt(paras[1]));
        b.setName(paras[3]);
        b.setAuthor(paras[5]);
        b.setPublisher(paras[7]);
        b.setPrice(Integer.parseInt(paras[9]));
        b.setStock(Integer.parseInt(paras[11]));
        b.setType(paras[13]);
        b.setPopularity(Integer.parseInt(paras[15]));
        return b;
    }

    private void fillJedis(Jedis jedis){
        Set<String> set =jedis.keys("*");
        if(set.isEmpty()) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.getTransaction();
            boolean b = tx.isActive();
            if (!b) {
                tx.begin();
            }
            Query query = session.createQuery("from Book");
            List list = query.list();
            Book[] bookArray = new Book[list.size()];
            list.toArray(bookArray);
            tx.commit();
            for(int i=0;i<bookArray.length;i=i+1){
                Book book=bookArray[i];
                jedis.set(String.valueOf(book.getId()),book.toString());
            }
        }
    }
}
