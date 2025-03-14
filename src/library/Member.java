package library;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import color.Color;

@SuppressWarnings({ "unused", "static-access" })
public class Member extends Person {

//	static ArrayList<BookHistory> borrowedBooks = new ArrayList<BookHistory>();
//	static ArrayList<BookHistory> returnedBooks = new ArrayList<BookHistory>();
	static byte booksCount;
	Library1 library = new Library1();

	int id = 0;

	LocalDateTime date = LocalDateTime.now();
	DateTimeFormatter formating = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	String date1 = date.format(formating);

	DBConnection connect = new DBConnection();

	Connection connection = connect.getConnectionObject();

	Member() {
		super();
	}

	Member(String userName, long mobileNumber) {
		super(userName, mobileNumber);
	}

	public void displayMemberDetails() {
		System.out.println(String.format("%-20s%-20s", userName, mobileNumber));
	}

	public void borrowBook(int bookId) {
		boolean bookFound = false;
//		if(this.booksCount >= 5)
//		{
//			System.out.println(Color.RED+"You reached the limit for borrow! Please return any book to borrow more books!"+Color.RESET);
//			return;
//		}
		if (library.books.size() == 0) {
			System.out.println(Color.RED + "Book id not found!" + Color.RESET);
			return;
		}

//		for(int j=0;j<borrowedBooks.size();j++)
//		{
//			if(borrowedBooks.get(j).getBookId() == bookId && !borrowedBooks.get(j).getStatus().equals("Available"))
//			{
//				System.out.println(Color.RED+"The Book was already borrowed"+Color.RESET);
//				return;
//			}
//		}

		for (int i = 0; i < library.books.size(); i++) {
			if (library.books.get(i).getBookId() == bookId && !(library.books.get(i).getCopies() < 0)) {

				if (connect.addBorrowedBooks(this.userName, bookId)) {

					library.books.get(i).setCopies(library.books.get(i).getCopies() - 1);
					Library1.booksHistory = connect.getTotalBorrowedBooks();
				}
				bookFound = true;
				System.out.println(Color.GREEN + "Book borrowed successfully!" + Color.RESET);
				break;
			}
		}
		if (!bookFound) {
			System.out.println(Color.RED + "Book Id not found" + Color.RESET);
			return;
		}

	}

	public void returnBook(int bookId) {
		BookHistory1 foundedBook = null;
		boolean bookFound = false;
		boolean found = false;
		boolean found1 = false;

		for (int i = 0; i < library.booksHistory.size(); i++) {

			if (library.booksHistory.get(i).getMemberName().equals(this.userName)
					&& library.booksHistory.get(i).getBorrowedBookId() == bookId) {
				bookFound = true;
				if (connect.returnBook(this.userName, bookId)) {

					library.books.get(i).setCopies(library.books.get(i).getCopies() + 1);
					Library1.booksHistory = connect.getTotalBorrowedBooks();
				}
				System.out.println(Color.GREEN + "Book returned Successfully!" + Color.RESET);
				break;
			}

		}
		if (!bookFound)
			System.out.println(Color.RED + "The Book id not found" + Color.RESET);
	}

	public void viewHistory() {

		System.out.println(Color.YELLOW
				+ "--------------------------------------------------Your Books History--------------------------------------------------"
				+ Color.RESET);
		System.out.println(String.format(Color.BLUE + "%-28s%-28s%-28s%-28s%-28s%-28s%-28s", "Borrow Id", "Member",
				"Book Id", "Borrowed Date", "Returned Date", "Status", "Fine" + Color.RESET));
		System.out.println("*".repeat(180));
		for (int i = 0; i < library.booksHistory.size(); i++) {
			if (library.booksHistory.get(i).memberName.equals(this.userName)) {
				System.out.println(library.booksHistory.get(i));
				System.out.println("*".repeat(180));
			}
		}
	}

	public void searchBooks(int id) {
		boolean booksFound = false;
		for (int i = 0; i < Library1.booksHistory.size(); i++) {
			if (library.booksHistory.get(i).memberName.equals(this.userName)
					&& library.booksHistory.get(i).borrowedBookId == id) {
				if (!booksFound) {
					System.out.println(String.format(Color.BLUE + "%-28s%-28s%-28s%-28s%-28s%-28s%-28s", "Borrow Id",
							"Member", "Book Id", "Borrowed Date", "Returned Date", "Status", "Fine" + Color.RESET));
					System.out.println("*".repeat(180));
				}
				System.out.println(library.booksHistory.get(i));
				System.out.println("*".repeat(180));
				booksFound = true;
			}
		}
		if (!booksFound) {
			System.out.println(Color.RED + "No one borrowed this book" + Color.RESET);
		}
	}
}
