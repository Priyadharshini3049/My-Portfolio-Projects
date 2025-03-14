package library;

import color.Color;

//import color.Color;

public class Book1 {

	private int bookId;
	private String title;
	private String author;
	private String genre;
	private int bookCount;

	Book1(int id, String book, String author, String category, int totalBooks) {
		bookId = id;
		title = book;
		this.author = author;
		this.genre = category;
		bookCount = totalBooks;
	}

	public void displayBookDetails() {
		System.out.println(String.format("%-40s%-40s%-40s%-40s%-40s", bookId, genre, title, author, bookCount));
		System.out.println("*".repeat(190));
	}

	public int getCopies() {
		return bookCount;
	}

	public void setCopies(int copy) {
		bookCount = copy;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
}
