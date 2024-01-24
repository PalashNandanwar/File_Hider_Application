package dao;

import DB.My_Connection;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_DAO {
    public static boolean isExists(String email) throws SQLException {
        Connection connection = My_Connection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select email from User_Info");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String e = rs.getString(1);
            if (e.equals(email))
                return true;
        }
        return false;
    }
    public static int saveUser(User user) throws SQLException {
        Connection connection = My_Connection.getConnection();
        PreparedStatement ps =connection.prepareStatement("insert into User_Info values(default, ?, ?)");
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        return ps.executeUpdate();
    }
}
