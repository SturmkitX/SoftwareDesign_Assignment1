package starters;

import implementations.UserDAOMySQL;
import interfaces.UserDAO;
import models.User;

public class Demo {
	
	
	public static void main(String[] args) {
		UserDAO ud = new UserDAOMySQL();
		User besina = ud.findUser("test@testus.com", "sidetest");
		System.out.println(besina);
	}
	
}
