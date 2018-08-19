package users.rest;

import lombok.Getter;
import users.domain.UserInfo;

@Getter
public class UserInfoResponse {
    public static final UserInfoResponse EMPTY_RESPONSE = new UserInfoResponse(new UserInfo());

    private final UserInfo userInfo;

    public UserInfoResponse(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
