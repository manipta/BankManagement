package dao;

import model.Admin;

import java.util.List;

public interface AdminDAO {
    public Admin getAdminByUsernameAndPassword(String username, String password);
}
