package library;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Date;
import java.sql.Date;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class DBConnection {
	public static Connection connection = null;

	public Connection getConnectionObject() {
		if (connection == null) {
			try {

				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library", "root", "prIy@1029");

			} catch (SQLException e) {
				System.out.println("Sorry! Couldn't connect it to Database...");
			}
		}
		return connection;
	}

	public void checkFine() {

		try {
			Statement st = connection.createStatement();
			ResultSet result = st.executeQuery("select expectedReturnedDate ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean insertBooks(String book, String author, String category, int available) {
		try {
			Statement st = connection.createStatement();
			ResultSet result = st.executeQuery("select cid from categories where category = '" + category + "';");

			int cid = 0;

			while (result.next()) {
				cid = result.getInt(1);
			}
			if (cid == 0) {
				PreparedStatement st1 = connection.prepareStatement("INSERT INTO categories (category) VALUES (?);");
				st1.setString(1, category);

				st1.executeUpdate();

				ResultSet result1 = st.executeQuery("select cid from categories where category = '" + category + "';");

				while (result1.next()) {
					cid = result1.getInt(1);
				}
			}

			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO Books (Book, Author,category,Available) VALUES (?, ?, ?, ?);");

			System.out.println(cid);

			statement.setString(1, book);
			statement.setString(2, author);
			statement.setInt(3, cid);
			statement.setInt(4, available);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println("Query execution failed...");

		}
		return false;
	}

	public boolean deleteBook(int id) {
		try {

			PreparedStatement statement = connection.prepareStatement("delete from Books where bookid = ?;");

			statement.setInt(1, id);

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0)
				return true;

		} catch (SQLException e) {
			System.out.println("Query execution failed..." + e.getMessage());

		}
		return false;
	}

	public boolean addMember(String name, long phone) {
		try {

			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO Members (Name, PhoneNumber,role) VALUES (?, ?, ?);");

			statement.setString(1, name);
			statement.setLong(2, phone);
			statement.setInt(3, 2);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println("Query execution failed...");

		}
		return false;
	}

	public boolean removeMember(String name) {
		try {

			PreparedStatement statement = connection.prepareStatement("delete from Members where Name = ?");

			statement.setString(1, name);

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println("Query execution failed...");

		}
		return false;
	}

	public boolean addBorrowedBooks(String member, int id) {
		try {

			Statement s1 = connection.createStatement();
			ResultSet result = s1.executeQuery("select memberid from Members where Name = '" + member + "';");
			int memberid = 0;
			while (result.next()) {
				memberid = result.getInt(1);
			}

			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO TotalBorrowedBooks (memberid,bookid,borrowedDate,expectedReturnedDate,status,Fine) VALUES (?, ?, ?,?,?,?);");

			statement.setInt(1, memberid);
			statement.setInt(2, id);
			statement.setDate(3, Date.valueOf(LocalDate.now()));
			statement.setDate(4, Date.valueOf(LocalDate.now().plusDays(15)));
			statement.setString(5, "Borrowed");
			statement.setDouble(6, 0);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println("Query execution failed...");

		}
		return false;
	}

	public boolean returnBook(String member, int id) {
		try {

			Statement s1 = connection.createStatement();
			ResultSet result = s1.executeQuery("select memberid from Members where Name = '" + member + "';");
			int memberid = 0;

			while (result.next()) {
				memberid = result.getInt(1);
			}

			PreparedStatement statement = connection
					.prepareStatement("UPDATE TotalBorrowedBooks SET status = ? WHERE memberid = ? AND bookid = ?");

			statement.setString(1, "Returned");
			statement.setInt(2, memberid);
			statement.setInt(3, id);

			int rowsAffected = statement.executeUpdate();

			return rowsAffected > 0;

		} catch (SQLException e) {
			System.out.println("Query execution failed...");

		}
		return false;
	}

	public ArrayList<Person> getMemberDetails() {

		ArrayList<Person> members = new ArrayList<>();

		try {

			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
					"select Members.memberid,Members.name,Members.PhoneNumber,roles.role from Members join roles on Members.role = roles.roleid;");

			while (result.next()) {
				int id = result.getInt(1);
				String memberName = result.getString(2);
				String phone = result.getString(3);
				String role = result.getString(4);

				if (role.equals("Librarian")) {
					Librarian librarian = new Librarian(memberName, Long.parseLong(phone));
					members.add(librarian);
				} else {
					Member member = new Member(memberName, Long.parseLong(phone));
					members.add(member);
				}
			}

		} catch (SQLException e) {
			System.out.println("Query execution failed...");
		}

		return members;
	}

	public ArrayList<Book1> getBookDetails() {
		ArrayList<Book1> books = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
					"select b.bookid,b.Book,b.Author,categories.category,b.Available from Books as b join categories on b.category = categories.cid;");
			while (result.next()) {
				int id = result.getInt(1);
				String book = result.getString(2);
				String author = result.getString(3);
				String genre = result.getString(4);
				int copies = result.getInt(5);

				Book1 book1 = new Book1(id, book, author, genre, copies);
				books.add(book1);
			}
		} catch (SQLException e) {
			System.out.println("Query Execution Failed...");
		}

		return books;
	}

	public ArrayList<BookHistory1> getTotalBorrowedBooks() {

		ArrayList<BookHistory1> booksHistory = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
					"select b.borrowId,m.Name,b.bookid,b.borrowedDate,b.expectedReturnedDate,b.status,b.fine from TotalBorrowedBooks as b join Members as m on b.memberid = m.memberid;");
			while (result.next()) {
				int id = result.getInt(1);
				String name = result.getString(2);
				int borrowedBookid = result.getInt(3);
				Date date1 = result.getDate(4);
				Date date2 = result.getDate(5);
				String status = result.getString(6);
				double fine = result.getDouble(7);

				BookHistory1 history = new BookHistory1(id, name, borrowedBookid, date1, date2, status, fine);

				booksHistory.add(history);
			}

		} catch (SQLException e) {
			System.out.println("Query execution failed...");
		}
		return booksHistory;
	}

}
