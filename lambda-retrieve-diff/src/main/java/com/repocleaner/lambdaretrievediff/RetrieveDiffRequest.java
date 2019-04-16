package com.repocleaner.lambdaretrievediff;

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
public class RetrieveDiffRequest {
    private String id;

    public void preCheck() {
        if (id == null) {
            clientError("id");
        }
    }

    private void clientError(String cause) {
        ResponseUtil.clientError("A valid " + cause);
    }
}
