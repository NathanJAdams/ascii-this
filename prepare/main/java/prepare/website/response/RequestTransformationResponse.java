package clean.website.response;

import java.util.UUID;

public class RequestTransformationResponse {
    private final boolean isValid;
    private final UUID id;

    public RequestTransformationResponse(boolean isValid, UUID id) {
        this.isValid = isValid;
        this.id = id;
    }

    public boolean isValid() {
        return isValid;
    }

    public UUID getId() {
        return id;
    }
}
