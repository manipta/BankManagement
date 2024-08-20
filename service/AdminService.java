package service;

import dao.AdminDAOImpl;
import model.Admin;

import java.sql.SQLException;

public class AdminService {
    private final AdminDAOImpl adminDao;

    public AdminService() {
        this.adminDao = new AdminDAOImpl();
    }

    public Admin loginAdmin(String username, String password) throws SQLException {
        return adminDao.getAdminByUsernameAndPassword(username, password);
    }
}

