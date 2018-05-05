package database;

import article.ArticleJson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    private static final Connection conn = ConnDriver.getInstance();

    private ArticleDAO() {

    }

    public static List<ArticleJson> getAllArticlesMetadata() {
        List<ArticleJson> result = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Articles");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
