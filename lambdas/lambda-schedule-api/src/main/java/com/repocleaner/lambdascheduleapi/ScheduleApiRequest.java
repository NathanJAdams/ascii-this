package com.repocleaner.lambdascheduleapi;

import com.repocleaner.model.Sink;
import com.repocleaner.model.Source;
import com.repocleaner.util.rest.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScheduleApiRequest {
    private final String userId;
    private final String token;
    private final Source source;
    private final Sink sink;

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
