package com.repocleaner.lambdascheduleweb;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.initiator.WebsiteInitiator;
import com.repocleaner.io.ScheduleIO;
import com.repocleaner.json.JsonInfo;
import com.repocleaner.model.Config;
import com.repocleaner.model.Initiator;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.model.Sink;
import com.repocleaner.model.Source;
import com.repocleaner.s3.S3ScheduleIO;
import com.repocleaner.sink.ZipFileSink;
import com.repocleaner.sink.ZipResponseType;
import com.repocleaner.source.RepoHostSource;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.rest.ResponseUtil;
import java.util.UUID;

public class ScheduleWebLambda implements RequestHandler<ScheduleWebRequest, ScheduleWebResponse> {
    private static final ScheduleIO SCHEDULE_IO = new S3ScheduleIO(JsonInfo.JSON_UTIL);
    private static final Config CONFIG = null;
    private static final Initiator INITIATOR = new WebsiteInitiator();

    @Override
    public ScheduleWebResponse handleRequest(ScheduleWebRequest request, Context context) {
        request.preCheck();
        String provider = request.getProvider();
        String user = request.getUser();
        String repo = request.getRepo();
        String branch = request.getBranch();

        String id = UUID.randomUUID().toString();
        Source source = new RepoHostSource(provider, user, repo, branch);
        Sink sink = new ZipFileSink(id, ZipResponseType.Diff);
        LifecycleRequest lifecycleRequest = new LifecycleRequest(id, INITIATOR, CONFIG, source, sink);
        try {
            SCHEDULE_IO.schedule(lifecycleRequest);
            return new ScheduleWebResponse(id);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return ResponseUtil.internalError("Schedule clean");
        }
    }
}
