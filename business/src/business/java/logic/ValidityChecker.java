package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidityChecker {
	
	private ValidityChecker() {
		
	}
	
	public static boolean checkValidEmail(String email) {
		Matcher m = Pattern.compile("\\S+@\\S+\\.\\S+").matcher(email);
		
		return m.find();
	}

}
