package Biz;

import Entity.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 47 on 2016/6/9.
 */
public interface BookBiz {
    //添加书籍
    public boolean addBook(Book book);
    //获取所有书籍
    public Book[] getAllBook();
    public Book[] getAllBook(String category);
    //根据hashMapde的bookId获取book对象, 并返回ShopCartItem对象的ArrayList
    public ArrayList getBookDetail(HashMap hashMap);
    public Book[] searchBook(String content);
    //更改书籍信息：parameters->需要更新的数据; user->需要将user中的book更新
    public void modifyBook(Map<String, Object> parameters);
    //删除书籍
    public void removeBook(int bookId);
    //得到一本书
    Book getBook(int bookId);

    void updateBook(Book book);

    void buyBook(int bookId,int num) throws Exception;

    void addPopularity(int bookId);
}
