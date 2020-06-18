package com.app.group15.UserManagement.ForgetPassword;

import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;

import java.util.List;
import java.util.Map;

public abstract class ForgetPasswordAbstractDao implements IDao {

    public abstract boolean checkIfTokenAlreadyExists(int id);

    public abstract boolean insertForgotPasswordToken(int id, String token);

    public abstract boolean deleteForgotPasswordToken(int id);

    public abstract Map<String, String> getUserFromToken(String token);

    public abstract boolean updateUserPassword(int userId, String password);

    @Override
    public Persistence get(int id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public int save(Persistence persistence) {
        return 0;
    }

    @Override
    public void update(Persistence persistence, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
