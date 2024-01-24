package Services;

import Model.User;
import dao.User_DAO;

import java.sql.SQLException;

public class User_Service {
    public static Integer saveUser(User user){
        try {
            if(User_DAO.isExists(user.getEmail())) {
                return 0;
            } else {
                return User_DAO.saveUser(user);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
