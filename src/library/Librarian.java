package library;

import java.sql.Connection;

import color.Color;

public class Librarian extends Person {

	int id;
	DBConnection connect = new DBConnection();

	Connection connection = connect.getConnectionObject();

	Librarian(String userName, long mobileNumber) {
		super(userName, mobileNumber);
	}

	public boolean addBooks(String bookName, String author, String genre, int availability) {
		if (!bookName.equals("") && !author.equals("") && !genre.equals("") && bookName != null && author != null
				&& genre != null) {
			boolean bookFound = false;
			for (int i = 0; i < Library1.books.size(); i++) {
				if (Library1.books.get(i).getTitle().equals(bookName)) {
					bookFound = true;
				}
			}
			if (!bookFound) {

				if (connect.insertBooks(bookName, author, genre, availability)) {
					Library1.books = connect.getBookDetails();
					return true;
				}
			}
		}
		return false;
	}

	public boolean removeBooks(int bookId) {

		boolean bookFound = false;
		for (int i = 0; i < Library1.books.size(); i++) {
			if (Library1.books.get(i).getBookId() == bookId) {
				bookFound = true;
			}
		}
		if (bookFound) {

			for (int i = 0; i < Library1.books.size(); i++) {
				if ((Library1.books.get(i)).getBookId() == (bookId)) {
					if (connect.deleteBook(bookId)) {
						Library1.books.remove(i);
						return true;
					}
				}
			}

		}

		return false;
	}

	public boolean addMember(String userName, long mobileNumber) {
		boolean memberFound = false;
		if (!userName.equals("")) {
			for (int i = 0; i < Library1.members.size(); i++) {
				if (Library1.members.get(i).userName.equals(userName)
						&& Library1.members.get(i).mobileNumber == mobileNumber) {
					memberFound = true;
				}
			}
			if (!memberFound) {
				if (connect.addMember(userName, mobileNumber)) {
					Library1.members = connect.getMemberDetails();
				}
				return true;
			}

		}
		return false;
	}

	public boolean removeMember(String userName) {
		boolean userFound = false;
		for (int i = 0; i < Library1.members.size(); i++) {
			if (Library1.members.get(i).userName.equals(userName)) {
				userFound = true;
			}
		}
		if (userFound) {
			for (int i = 0; i < Library1.members.size(); i++) {
				if (Library1.members.get(i).userName.equals(userName)) {
					System.out.println(userName);
					if (connect.removeMember(userName)) {
						Library1.members.remove(i);
						return true;
					}
				}
			}
		}
		return false;
	}

//	public void viewBooks()
//	{
//		System.out.println("*".repeat(190));
//		System.out.println(String.format(Color.BLUE+"%-40s%-40s%-40s%-40s%-40s", "Book ID", "Genre", "Book Name", "Author","Availability"+Color.RESET));
//		for(int i=0;i<Library1.books.size();i++)
//		{
//			Library1.books.get(i).displayBookDetails();
//		}
//	}

	public void viewMemberDetails() {
		if (Library1.members.size() < 1) {
			System.out.println(Color.RED + "No members were added" + Color.RESET);
			return;
		}
		System.out.println("*".repeat(100));
		System.out.println(String.format(Color.BLUE + "%-20s%-20s", "User Name", "Mobile Number" + Color.RESET));
		System.out.println("*".repeat(100));
		for (int i = 0; i < Library1.members.size(); i++) {
			if (!(Library1.members.get(i) instanceof Librarian)) {
				Library1.members.get(i).displayMemberDetails();
				System.out.println("*".repeat(100));
			}

		}
	}

	public void totalBorrowedBooksHistory() {
		if (Library1.booksHistory.size() == 0) {
			System.out.println(Color.RED + "No books were borrowed" + Color.RESET);
		} else {

			System.out.println(Color.YELLOW
					+ "--------------------------------------------------Total Books Borrowed from the Library--------------------------------------------------"
					+ Color.RESET);
			System.out.println(String.format(Color.BLUE + "%-28s%-28s%-28s%-28s%-28s%-28s%-28s", "Borrow Id", "Member",
					"Book Id", "Borrowed Date", "Returned Date", "Status", "Fine" + Color.RESET));
			System.out.println("*".repeat(180));
			for (int i = 0; i < Library1.booksHistory.size(); i++) {
				System.out.println(Library1.booksHistory.get(i));
				System.out.println("*".repeat(180));
			}
		}
	}

	public void searchBooks(int id) {
		boolean booksFound = false;
		for (int i = 0; i < Library1.booksHistory.size(); i++) {
			if (Library1.booksHistory.get(i).borrowedBookId == id) {
				if (!booksFound) {
					System.out.println(String.format(Color.BLUE + "%-28s%-28s%-28s%-28s%-28s%-28s%-28s", "Borrow Id",
							"Member", "Book Id", "Borrowed Date", "Returned Date", "Status", "Fine" + Color.RESET));
					System.out.println("*".repeat(180));
				}
				System.out.println(Library1.booksHistory.get(i));
				System.out.println("*".repeat(180));
				booksFound = true;
			}
		}
		if (!booksFound) {
			System.out.println(Color.RED + "No one borrowed this book" + Color.RESET);
		}
	}

}
