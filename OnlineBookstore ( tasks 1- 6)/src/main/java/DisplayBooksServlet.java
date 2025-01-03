import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/displayBooks")
public class DisplayBooksServlet extends HttpServlet {
    private final DBConnectionManager dbManager = new DBConnectionManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head><title>Book List</title></head>");
        writer.println("<body>");
        writer.println("<h1>Book List</h1>");
        writer.println("<table border='1'>");
        writer.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th></tr>");

        // Fetch book details from the database
        try (Connection connection = dbManager.openConnection()) {
            String sql = "SELECT oid, otitle, oauthor, oprice FROM Books";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("oid");
                String title = rs.getString("otitle");
                String author = rs.getString("oauthor");
                double price = rs.getDouble("oprice");

                writer.println("<tr>");
                writer.println("<td>" + id + "</td>");
                writer.println("<td>" + title + "</td>");
                writer.println("<td>" + author + "</td>");
                writer.println("<td>" + price + "</td>");
                writer.println("</tr>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching books.");
        }

        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");
    }

    @WebServlet("/displayBooks")
    public static class DeleteBookServlet extends HttpServlet {
        private final DBConnectionManager dbManager = new DBConnectionManager();

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();

            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head><title>Book List</title></head>");
            writer.println("<body>");
            writer.println("<h1>Book List</h1>");
            writer.println("<table border='1'>");
            writer.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th></tr>");

            // Fetch book details from the database
            try (Connection connection = dbManager.openConnection()) {
                String sql = "SELECT oid, otitle, oauthor, oprice FROM Books";
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("oid");
                    String title = rs.getString("otitle");
                    String author = rs.getString("oauthor");
                    double price = rs.getDouble("oprice");

                    writer.println("<tr>");
                    writer.println("<td>" + id + "</td>");
                    writer.println("<td>" + title + "</td>");
                    writer.println("<td>" + author + "</td>");
                    writer.println("<td>" + price + "</td>");
                    writer.println("</tr>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching books.");
            }

            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");
        }
    }
}
