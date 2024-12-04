package user;

public class UserCreds {

        private String password;
        private String email;

        public UserCreds(String email, String password) {
            this.email = email;
            this.password = password;
        }
        public static UserCreds credsFromUser(CreateUser createUser){

            return new UserCreds(createUser.getEmail(), createUser.getPassword());
        }

}
