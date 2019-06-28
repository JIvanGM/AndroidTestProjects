package mx.com.ia.androidtesterapp.Interfaces;

import mx.com.ia.androidtesterapp.Objects.User;

public interface UserDao {

    User getUser(int id);

    void storeUser(User user);

}
