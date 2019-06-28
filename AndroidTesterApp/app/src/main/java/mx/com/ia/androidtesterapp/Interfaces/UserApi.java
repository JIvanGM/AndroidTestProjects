package mx.com.ia.androidtesterapp.Interfaces;

import mx.com.ia.androidtesterapp.Objects.User;

public interface UserApi {
    User getUser(int id);
    void updateUser(User user);
}
