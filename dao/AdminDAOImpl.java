package dao;

import model.Admin;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public Admin getAdminByUsernameAndPassword(String username, String password){
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Admin(
                        resultSet.getInt("admin_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
            }
            return null;
        }catch (SQLException e) {
            throw new RuntimeException("Error adding account", e);
        }
    }
}

