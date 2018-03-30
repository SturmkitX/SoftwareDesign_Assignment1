package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.UserAccess;
import models.User;

public class ValidityChecker {
	
	public static enum ErrorCode {
		OK, INCOMPLETE_FIELDS, USER1_NOT_EXIST, USER2_NOT_EXIST, USER1_NAN, USER2_NAN
	}
	
	private ValidityChecker() {
		
	}
	
	public static boolean checkValidEmail(String email) {
		Matcher m = Pattern.compile("\\S+@\\S+\\.\\S+").matcher(email);
		
		return m.find();
	}
	
	/*
	 * returned Error Codes
	 */
	public static ErrorCode checkValidAddedPlayers(String p1, String p2) {
		if(p1.isEmpty() || p2.isEmpty()) {
			return ErrorCode.INCOMPLETE_FIELDS;
		}
		
		int p1Id = 0;
		int p2Id = 0;
		
		try {
			p1Id = Integer.parseInt(p1);
		} catch(NumberFormatException e) {
			return ErrorCode.USER1_NAN;
		}
		
		try {
			p2Id = Integer.parseInt(p2);
		} catch(NumberFormatException e) {
			return ErrorCode.USER2_NAN;
		}
		
		
		User u1 = UserAccess.getUserById(p1Id);
		User u2 = UserAccess.getUserById(p2Id);
		
		if(u1 == null) {
			return ErrorCode.USER1_NOT_EXIST;
		}
		
		if(u2 == null) {
			return ErrorCode.USER2_NOT_EXIST;
		}
		
		return ErrorCode.OK;
	}

}
