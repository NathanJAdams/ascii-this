package com.repocleaner.lambdaencryptrepotoken;

import com.repocleaner.util.rest.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EncryptRepoTokenRequest {
    private String token;

    public void preCheck() {
        if (token == null) {
            ResponseUtil.clientError("A valid token");
        }
    }
}
