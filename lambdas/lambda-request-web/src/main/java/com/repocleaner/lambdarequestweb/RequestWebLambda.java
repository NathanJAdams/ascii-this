package com.repocleaner.lambdarequestweb;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.initiator.WebsiteInitiator;
import com.repocleaner.io.RequestIO;
import com.repocleaner.json.JsonInfo;
import com.repocleaner.model.Config;
import com.repocleaner.model.Initiator;
import com.repocleaner.model.CleanRequest;
import com.repocleaner.model.Sink;
import com.repocleaner.model.Source;
import com.repocleaner.s3.S3RequestIO;
import com.repocleaner.sink.ZipFileSink;
import com.repocleaner.sink.ZipResponseType;
import com.repocleaner.source.RepoHostSource;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.rest.ResponseUtil;
import java.util.UUID;

public class RequestWebLambda implements RequestHandler<RequestWebRequest, RequestWebResponse> {
    private static final RequestIO SCHEDULE_IO = new S3RequestIO(JsonInfo.JSON_UTIL);
    private static final Config CONFIG = null;
    private static final Initiator INITIATOR = new WebsiteInitiator();

    @Override
    public RequestWebResponse handleRequest(RequestWebRequest request, Context context) {
        request.preCheck();
        String provider = request.getProvider();
        String user = request.getUser();
        String repo = request.getRepo();
        String branch = request.getBranch();

        String id = UUID.randomUUID().toString();
        Source source = new RepoHostSource(provider, user, repo, branch);
        Sink sink = new ZipFileSink(id, ZipResponseType.Diff);
        CleanRequest cleanRequest = new CleanRequest(id, INITIATOR, CONFIG, source, sink);
        try {
            SCHEDULE_IO.request(cleanRequest);
            return new RequestWebResponse(id);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return ResponseUtil.internalError("Schedule clean");
        }
    }
}
