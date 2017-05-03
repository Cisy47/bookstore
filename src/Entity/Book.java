package Entity;

public class Book {
	private int id;
	private String name;
	private String author;
	private String publisher;
	private int price;
	private int stock;
	private String type;
	private int popularity;

	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock){
		this.stock = stock;
	}
	public void copy(Book book){
		name = book.getName();
		author = book.getAuthor();
		publisher = book.getPublisher();
		price = book.getPrice();
		stock = book.getStock();
	}

	@Override
	public String toString() {
		return "id@" + id+ '@'+
				"name@" + name + '@' +
				"author@" + author + '@' +
				"publisher@" + publisher + '@' +
				"price@" + price + '@'+
				"stock@" + stock +'@'+
				"type@" + type + '@'+
				"popularity@" + popularity
				;
	}

}
