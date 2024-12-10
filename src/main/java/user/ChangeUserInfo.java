package user;

public class ChangeUserInfo {

    private String email;
    private String password;
    private String name;

    public String getName() {
        return name;
    }


    public String getPassword() {

        return password;
    }
    public String getEmail() {

        return email;
    }

    public ChangeUserInfo withName(String name) {
        this.name = name;
        return this;
    }

    public ChangeUserInfo withPassword(String password) {
        this.password = password;
        return this;
    }


    public ChangeUserInfo withEmail(String email) {
        this.email = email;
        return this;
    }

}
