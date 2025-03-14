package library;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import color.Color;

@SuppressWarnings({ "unused", "resource" })
public class Library1 {

	static ArrayList<Person> members;
	static ArrayList<Book1> books;
	static ArrayList<BookHistory1> booksHistory;

	public static void main(String[] args) {

		DBConnection connect = new DBConnection();

		Connection connection = connect.getConnectionObject();

		members = connect.getMemberDetails();
		books = connect.getBookDetails();
		booksHistory = connect.getTotalBorrowedBooks();

		Scanner scanner = new Scanner(System.in);

		System.out.println("\n" + Color.BACKGROUND_YELLOW + Color.PURPLE
				+ "*--*--*--*--*--*--*--*--*--*--*--* Welcome to our Library *--*--*--*--*--*--*--*--*--*--*--*"
				+ Color.RESET + "\n");

		short logoutChoice = 0;
		int maxAttempts = 0;
		while (logoutChoice != 1) {

			System.out.println("Enter your user Name :");
			String userName = scanner.nextLine();
			long mobileNumber = 0;
			System.out.println("Enter your Mobile Number :");
			try {
				mobileNumber = scanner.nextLong();
			} catch (InputMismatchException e) {
				System.out.println("You are entered wrong input");
			}
			System.out.print("\n");
			boolean userFound = false;
			String userChoice = "";

			Login1 login = new Login1();

			Person loginUser = login.authenticate(userName, mobileNumber);

			if (loginUser instanceof Librarian) {
				Librarian librarian = (Librarian) loginUser;
				userFound = true;
				while (!userChoice.equals("8")) {
					System.out.println(
							"What do you want to do?\n1)Add Books \n2)Remove Books \n3)Add Member \n4)Remove Member \n5)View Books \n6)View Member Details \n7)Books borrowed and returned History \n8)Exit");
					userChoice = scanner.nextLine();
					switch (userChoice) {
					case "1":
						System.out.println("Enter the book name: ");
						String bookName = scanner.nextLine();
						System.out.println("Who is the author of this book? ");
						String author = scanner.nextLine();
						System.out.println("Which genre is this? ");
						String category = scanner.nextLine();
						System.out.println("How many copies are available? ");
						int availability = 0;
						try {
							availability = scanner.nextInt();
						} catch (InputMismatchException e) {
							System.out.println(Color.RED + "Sorry! You didn't give a right value." + Color.RED);
						}
						if (librarian.addBooks(bookName, author, category, availability)) {
							System.out.println(Color.GREEN + "Book added successfully!" + Color.RESET);
						} else {
							System.out.println(Color.RED + "Sorry! Can't add this book!" + Color.RESET);
						}
						break;
					case "2":
						loginUser.viewBooks();
						int bookId = 0;
						System.out.println("Enter the bookId from the above books : ");
						try {
							bookId = scanner.nextInt();
						} catch (InputMismatchException e) {
							e.getMessage();
						}
						if (librarian.removeBooks(bookId)) {
							System.out.println(Color.GREEN + "Book removed Successfully" + Color.RESET);
						} else {
							System.out.println(Color.RED + "Book Id not found!" + Color.RESET);
						}
						break;
					case "3":
						System.out.println("Enter the user Name of the Member: ");
						userName = scanner.nextLine();
						System.out.println("Enter the mobile Number: ");
						mobileNumber = scanner.nextLong();
						if (librarian.addMember(userName, mobileNumber)) {
							System.out.println(Color.GREEN + "Member added Successfully" + Color.RESET);
						} else {
							System.out.println(Color.RED + "Member already exists!" + Color.RESET);
						}
						break;
					case "4":
						System.out.println("Enter the user Name of the member: ");
						userName = scanner.nextLine();
						if (librarian.removeMember(userName)) {
							System.out.println(Color.GREEN + "Member removed Successfully!" + Color.RESET);
						} else {
							System.out.println(Color.RED + "Member Not found" + Color.RESET);
						}
						break;
					case "5":
						librarian.viewBooks();
						break;
					case "6":
						librarian.viewMemberDetails();
						break;
					case "7":
						System.out.println("Do you want search books by it's id? \n1)Search \n2)View History");
						byte search = 2;
						try {
							search = scanner.nextByte();
						} catch (InputMismatchException e) {
							System.out.println(Color.RED + "You are entered a wrong input" + Color.RESET);
						}
						if (search == 1) {
							System.out.println("Enter the id of the Book");
							int id = scanner.nextInt();
							librarian.searchBooks(id);
						}
						if (search == 2)
							librarian.totalBorrowedBooksHistory();
						break;

					}
				}
				logoutChoice = 1;
			} else if (loginUser instanceof Member) {
				Member member = (Member) loginUser;

				userFound = true;
				while (!userChoice.equals("5")) {
					System.out.println(
							"What do you want to do? \n1)View Book \n2)Borrow Book \n3)Return Book \n4)View History \n5)Exit");
					userChoice = scanner.nextLine();
					switch (userChoice) {
					case "1":
						loginUser.viewBooks();
						break;
					case "2":
						loginUser.viewBooks();
						System.out.println("Enter the bookId of the book you want to borrow: ");
						int bookId = scanner.nextInt();
						scanner.nextLine();
						member.borrowBook(bookId);

						break;
					case "3":
						System.out.println("Enter the bookId of the book you want to return: ");
						bookId = scanner.nextInt();
						member.returnBook(bookId);
						break;
					case "4":
						System.out.println("Do you want search books by it's status? \n1)Search \n2)View History");
						byte search = 2;
						try {
							search = scanner.nextByte();
						} catch (InputMismatchException e) {
							System.out.println(Color.RED + "You are entered a wrong input" + Color.RESET);
						}
						if (search == 1) {
							System.out.println("Enter the id of the Book");
							int id = scanner.nextInt();
							member.searchBooks(id);
						}
						if (search == 2)
							member.viewHistory();
						break;

					}
				}
				logoutChoice = 1;
			} else {
				maxAttempts++;
				if (maxAttempts >= 3) {
					System.out.println(Color.RED + "Sorry! You reached the maximum limits to login. Try again later..."
							+ Color.RESET);
					logoutChoice = 1;
				}
			}

		}
	}

}
