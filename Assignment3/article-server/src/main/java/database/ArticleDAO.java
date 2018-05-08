package database;

import article.Article;
import user.User;

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

    public static List<Article> getAllArticlesMetadata() {
        List<Article> result = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, title, abstract, author_id FROM Articles");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Article a = new Article();
                a.setId(rs.getString("id"));
                a.setTitle(rs.getString("title"));
                a.setArticleAbstract(rs.getString("abstract"));

                User author = UserDAO.findUser(rs.getInt("author_id"));
                author.setRole(0);
                author.setPassword("");
                a.setAuthor(author);
                result.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getArticleBodyPath(String id) {
        String result = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT body FROM Articles WHERE id = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next()) {
                return null;
            }

            result = rs.getString("body");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void insertArticle(Article a, String bodyPath) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Articles (id, title, abstract, author_id, body) " +
                    "VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, a.getId());
            stmt.setString(2, a.getTitle());
            stmt.setString(3, a.getArticleAbstract());
            stmt.setInt(4, a.getAuthor().getId());
            stmt.setString(5, bodyPath);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
