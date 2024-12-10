package user;

public class CreateUser {

    private String email;
    private String password;
    private String name;



    public String getPassword() {

        return password;
    }
    public String getEmail() {

        return email;
    }

    public CreateUser withName(String name) {
        this.name = name;
        return this;
    }

    public CreateUser withPassword(String password) {
        this.password = password;
        return this;
    }


    public CreateUser withEmail(String email) {
        this.email = email;
        return this;
    }




}
