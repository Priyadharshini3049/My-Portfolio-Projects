package library;

import color.Color;

public class Person implements CommonMethods {
	String userName;
	long mobileNumber;

	Person(String userName, long mobileNumber) {
		this.userName = userName;
		this.mobileNumber = mobileNumber;
	}

	Person() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void displayMemberDetails() {
		Member member = new Member();
		member.displayMemberDetails();
	}

	@Override
	public void viewBooks() {
		System.out.println("*".repeat(190));
		System.out.println(String.format(Color.BLUE + "%-40s%-40s%-40s%-40s%-40s", "Book ID", "Genre", "Book Name",
				"Author", "Availability" + Color.RESET));
		for (int i = 0; i < Library1.books.size(); i++) {
			Library1.books.get(i).displayBookDetails();
		}
	}

}
