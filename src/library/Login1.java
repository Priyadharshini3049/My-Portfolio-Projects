package library;

public class Login1 {
	public Person authenticate(String name, long phone) {
		for (Person p : Library1.members) {
			if (p.getUserName().equals(name) && p.getMobileNumber() == phone) {

				return p;
			}
		}
		return null;
	}
}
