package mx.com.ia.androidtesterapp.Implementations;


import mx.com.ia.androidtesterapp.Interfaces.UserApi;
import mx.com.ia.androidtesterapp.Interfaces.UserDao;
import mx.com.ia.androidtesterapp.Interfaces.UserRepository;
import mx.com.ia.androidtesterapp.Objects.User;

public class UserRepositoryImpl implements UserRepository {

    UserDao userDao;
    UserApi userApi;

    public UserRepositoryImpl(UserDao userDao, UserApi userApi) {
        this.userApi = userApi;
        this.userDao = userDao;
    }

    @Override
    public User getUser(int id) {
        User user = userDao.getUser(id);

        if (user != null)
            return user;
        else {
            user = userApi.getUser(id);

            try {
                userDao.storeUser(user);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error de almacenamiento", e);
            }

            return user;
        }
    }

    @Override
    public void updateUser(User user) {
        userApi.updateUser(user);
        userDao.storeUser(user);
    }
}
