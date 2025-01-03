import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/*")  // Handles all incoming requests
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo(); // Get path after the context root

        if (path == null || path.equals("/")) {
            response.getWriter().println("Welcome to the Bookstore Application!");
            return;
        }

        // Route requests based on URL path
        switch (path) {
            case "/registerTask":
                // Forward to BookRegistrationServlet
                request.getRequestDispatcher("/registerTaskServlet").forward(request, response);
                break;

            case "/displayBooks":
                // Forward to DisplayBooksServlet
                request.getRequestDispatcher("/displayBooksServlet").forward(request, response);
                break;

            default:
                // Handle 404 Not Found
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page Not Found.");
        }
    }
}
