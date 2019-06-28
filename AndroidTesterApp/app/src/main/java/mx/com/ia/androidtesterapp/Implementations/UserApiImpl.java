package mx.com.ia.androidtesterapp.Implementations;

import mx.com.ia.androidtesterapp.Interfaces.UserApi;
import mx.com.ia.androidtesterapp.Objects.User;

public class UserApiImpl implements UserApi {

    @Override
    public User getUser(int id) {
        return new User(id);
    }

    @Override
    public void updateUser(User user) {

    }
}
