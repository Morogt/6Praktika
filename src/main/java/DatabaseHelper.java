import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/test5";
    private static final String USER = "root";
    private static final String PASSWORD = "123123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static User validateUser(String username, String password) {
        String query = "SELECT * FROM user WHERE UserLogin = ? AND UserPassword = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int role = resultSet.getInt("UserRoleID");
                String name1 = resultSet.getString("UserSurname");
                String name2 = resultSet.getString("UserName");
                String name3 = resultSet.getString("UserPatronymic");
                String fullName = name1 + " " + name2 + " " + name3;
                return new User(username, role, fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new User(username, 0, " ");
        }
        return new User(username, 0, " ");
    }

    public static boolean addProduct(Product product) {
        String query = "INSERT INTO Product (SKU, ProductName, UnitOfMeasurement, Cost, MaxDiscountSize, " +
                "Manufacturer, Supplier, Category, CurrentDiscount, QuantityInStock, Description, Image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.getSKU());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setInt(3, product.getUnitOfMeasurement());
            preparedStatement.setBigDecimal(4, product.getCost());
            preparedStatement.setBigDecimal(5, product.getMaxDiscountSize());
            preparedStatement.setInt(6, product.getManufacturer());
            preparedStatement.setInt(7, product.getSupplier());
            preparedStatement.setInt(8, product.getCategory());
            preparedStatement.setBigDecimal(9, product.getCurrentDiscount());
            preparedStatement.setInt(10, product.getQuantityInStock());
            preparedStatement.setString(11, product.getDescription());
            preparedStatement.setString(12, product.getImage());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
