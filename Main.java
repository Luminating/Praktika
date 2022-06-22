import java.sql.*;

public class Main {
    private static Connection connection;
    private static final String url = "jdbc:postgresql://localhost:5432/praktikadb";
    private static final String username = "postgres";
    private static final String password = "Pavel_10";

    private static final String insertUser = "INSERT INTO users (u_name, u_login, u_password, u_email, u_role_id, u_language_id) " +
            "VALUES (?, ?, ?, ?, (SELECT r_id FROM roles WHERE r_name = ?), (SELECT l_id FROM languages WHERE l_name = ?))";
    private static final String deleteUserById = "DELETE FROM users WHERE u_id = ?";
    private static final String selectUserByName = "SELECT * FROM users WHERE u_name = ?";
    private static final String updateUserById = "UPDATE users SET u_name = ?, u_login = ?, u_password = ?, u_email = ?, u_role_id = ?, u_language_id = ? WHERE u_id = ?";

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            //insertUser("Irina Skiba", "Irina", "12345", "irina@mail.ru", "User", "Russian");
            //deleteUserById(6);
            selectUserByName("Ivan Petrov");
            //updateUserById(10, "Alexey Ivanov", "Alexey", "12345", "alex@mail.ru", 8, 2);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUser(String name, String login, String password, String email, String role, String lang) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(insertUser);
        statement.setString(1, name);
        statement.setString(2, login);
        statement.setString(3, password);
        statement.setString(4, email);
        statement.setString(5, role);
        statement.setString(6, lang);
        statement.execute();
    }

    public static void deleteUserById(int uid) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(deleteUserById);
        statement.setInt(1, uid);
        statement.execute();
    }

    public static void selectUserByName(String name) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(selectUserByName);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getInt("u_id") + "; " +
                    resultSet.getString("u_name") + "; " +
                    resultSet.getString("u_login") + "; " +
                    resultSet.getString("u_password") + "; " +
                    resultSet.getString("u_email") + "; " +
                    resultSet.getInt("u_role_id") + "; " +
                    resultSet.getInt("u_language_id"));
        }

    }

    public static void updateUserById(int uid, String newName, String newLogin, String newPassword, String newEmail, int newRoleId, int newLangId) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(updateUserById);
        statement.setString(1, newName);
        statement.setString(2, newLogin);
        statement.setString(3, newPassword);
        statement.setString(4, newEmail);
        statement.setInt(5, newRoleId);
        statement.setInt(6, newLangId);
        statement.setInt(7, uid);
        statement.execute();
    }
}
