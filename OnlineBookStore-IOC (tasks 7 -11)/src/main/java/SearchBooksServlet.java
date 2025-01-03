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

@WebServlet("/searchBooks")
public class SearchBooksServlet extends HttpServlet {

    private final DBConnectionManager dbManager;

    // Constructor Injection via Spring, or manual instantiation if not using Spring
    public SearchBooksServlet(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the search query from request parameters
        String titleQuery = request.getParameter("title");

        // Prepare the HTML response
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        try (Connection connection = dbManager.openConnection()) {
            // Construct SQL query to search for books by title (case-insensitive)
            String sql = "SELECT * FROM Books WHERE title LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + titleQuery + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            // Build the response with search results
            writer.println("<html><body>");
            writer.println("<h1>Search Results for '" + titleQuery + "'</h1>");

            boolean hasResults = false;
            while (resultSet.next()) {
                hasResults = true;
                writer.println("<p>");
                writer.println("<b>Title:</b> " + resultSet.getString("title") + "<br>");
                writer.println("<b>Author:</b> " + resultSet.getString("author") + "<br>");
                writer.println("<b>Price:</b> $" + resultSet.getDouble("price") + "<br>");
                writer.println("</p>");
            }

            if (!hasResults) {
                writer.println("<p>No books found matching the title '" + titleQuery + "'</p>");
            }

            writer.println("</body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error searching for books.");
        }
    }
}