
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/registerTask")
public class BookRegistrationServlet extends HttpServlet {
    private final DBConnectionManager dbManager = new DBConnectionManager();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        double price = Double.parseDouble(request.getParameter("price"));

        try (Connection connection = dbManager.openConnection()) {
            String sql = "INSERT INTO Books (title,author, price) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDouble(3, price);
            stmt.executeUpdate();

            PrintWriter writer = response.getWriter();
            writer.println("Task registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error registering task.");
        }
    }
}
