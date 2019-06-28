package mx.com.ia.androidtesterapp.Objects;

public class User {
    String userName;
    int id;

    public User(int id) {
        this.id = id;
        this.userName = "Usuario " + id;
    }

    public User(int id, String name) {
        this.id = id;
        this.userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }
}
