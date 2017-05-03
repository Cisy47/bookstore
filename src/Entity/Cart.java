package Entity;

import java.util.ArrayList;

/**
 * Created by 47 on 2017/3/16.
 */
public class Cart {
    ArrayList<Book> books;
    ArrayList<Integer> booknums;

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Integer> getBooknums() {
        return booknums;
    }

    public void setBooknums(ArrayList<Integer> booknums) {
        this.booknums = booknums;
    }


}
