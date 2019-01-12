package com.repocleaner.lambdaretrievediff;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.s3.S3FileStructure;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.rest.ResponseUtil;

public class RetrieveDiffLambda implements RequestHandler<RetrieveDiffRequest, RetrieveDiffResponse> {
    @Override
    public RetrieveDiffResponse handleRequest(RetrieveDiffRequest request, Context context) {
        request.preCheck();
        String id = request.getId();
        try {
            String diff = S3FileStructure.downloadDiff(id);
            return new RetrieveDiffResponse(diff);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return ResponseUtil.internalError("Retrieve diff");
        }
    }
}
