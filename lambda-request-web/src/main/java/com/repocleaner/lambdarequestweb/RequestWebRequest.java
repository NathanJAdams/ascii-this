package com.repocleaner.lambdarequestweb;

import com.repocleaner.core.util.rest.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestWebRequest {
    private String provider;
    private String user;
    private String repo;
    private String branch;

    public void preCheck() {
        if (provider == null) {
            clientError("provider");
        }
        if (user == null) {
            clientError("user");
        }
        if (repo == null) {
            clientError("repo");
        }
        if (branch == null) {
            clientError("branch");
        }
    }

    private void clientError(String cause) {
        ResponseUtil.clientError("A valid " + cause);
    }
}
