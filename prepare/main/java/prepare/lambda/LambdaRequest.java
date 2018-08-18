package clean.lambda;

import clean.source.Source;
import clean.user.User;

public class LambdaRequest {
    private final User user;
    private final Source source;

    public LambdaRequest(User user, Source source) {
        this.user = user;
        this.source = source;
    }

    public User getUser() {
        return user;
    }

    public Source getSource() {
        return source;
    }
}
