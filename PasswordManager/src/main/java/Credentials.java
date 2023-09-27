public class Credentials {
    private String password;
    private String login;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }



    public Credentials(String password, String login) {
        this.password = password;
        this.login = login;
    }
    public Credentials() {}
}
