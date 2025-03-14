package library;

import java.util.Date;

public class BookHistory1 {

	int borrowid;
	String memberName;
	int borrowedBookId;
	Date borrowedDate;
	Date expectedReturned;
	String status;
	double fine = 0.0;

	BookHistory1(int borrowid, String member, int bookid, Date borrowedDate, Date returnedDate, String status,
			double fine) {
		this.borrowid = borrowid;
		memberName = member;
		borrowedBookId = bookid;
		this.borrowedDate = borrowedDate;
		expectedReturned = returnedDate;
		this.status = status;
		if (fine > 0) {
			this.fine = fine;
		}
	}

	public int getBorrowid() {
		return borrowid;
	}

	public void setBorrowid(int borrowid) {
		this.borrowid = borrowid;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getBorrowedBookId() {
		return borrowedBookId;
	}

	public void setBorrowedBookId(int borrowedBookId) {
		this.borrowedBookId = borrowedBookId;
	}

	public Date getBorrowedDate() {
		return borrowedDate;
	}

	public void setBorrowedDate(Date borrowedDate) {
		this.borrowedDate = borrowedDate;
	}

	public Date getExpectedReturned() {
		return expectedReturned;
	}

	public void setExpectedReturned(Date expectedReturned) {
		this.expectedReturned = expectedReturned;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	@Override
	public String toString() {
		return String.format("%-28s%-28s%-28s%-28s%-28s%-28s%-28s", borrowid, memberName, borrowedBookId, borrowedDate,
				expectedReturned, status, fine);
	}

}
