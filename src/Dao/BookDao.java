package Dao;

import Entity.Book;

/* BookDao 书籍持久化操作类
 * */

public interface BookDao {
	//添加书籍
	public boolean addBook(Book book);
	//获取所有书
	public Book[] getAllBook();
	public Book[] getAllBook(String category);
	//搜索书籍
	public Book[] searchBook(String content);
	//删除书籍
	public void removeBook(int bookId);
	//查询书籍(通过ID)
	public Book getBookById(int bookId);
	//更新书籍
	public void updateBook(Book book);
}
