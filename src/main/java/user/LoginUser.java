package user;

public class LoginUser {


    private String password;
    private String email;



    public LoginUser withPassword(String password) {
        this.password = password;
        return this;
    }


    public LoginUser withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
