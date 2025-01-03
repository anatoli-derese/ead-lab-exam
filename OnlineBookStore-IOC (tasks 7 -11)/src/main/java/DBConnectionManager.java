import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Setter
@NoArgsConstructor
public class DBConnectionManager {
    private String url = "jdbc:mysql://localhost:3306/BookstoreDB";
    private String username = "anatoli";
    private String password = "abc123";

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
