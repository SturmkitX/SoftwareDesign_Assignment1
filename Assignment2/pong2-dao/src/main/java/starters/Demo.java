package starters;

import implementations.mysql.UserDAOImplem;
import interfaces.UserDAO;
import entities.User;

import java.sql.SQLException;

public class Demo {


    public static void main(String[] args) throws SQLException {
        UserDAO ud = new UserDAOImplem();
        User testUser = ud.findUserByEmailAndPassword("test@testus.com", "sidetest");
        System.out.println(testUser);
    }

}