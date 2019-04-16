package com.repocleaner.lambdarequestapi;

import com.repocleaner.core.model.Sink;
import com.repocleaner.core.model.Source;
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
public class RequestApiRequest {
    private String userId;
    private String token;
    private Source source;
    private Sink sink;

    public void preCheck() {
        if (userId == null) {
            clientError("user id");
        }
        if (token == null) {
            clientError("token");
        }
        if (source == null) {
            clientError("source");
        }
        if (sink == null) {
            clientError("sink");
        }
    }

    private void clientError(String cause) {
        ResponseUtil.clientError("A valid " + cause);
    }
}
