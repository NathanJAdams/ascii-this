package users.rest;

public class UserInfoRequest {
    private final String jwt;

    public UserInfoRequest(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
