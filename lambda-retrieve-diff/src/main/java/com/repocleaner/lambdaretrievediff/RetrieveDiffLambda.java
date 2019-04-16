package com.repocleaner.lambdaretrievediff;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.core.util.RepoCleanerException;
import com.repocleaner.core.util.rest.ResponseUtil;
import com.repocleaner.s3.S3FileStructureConnector;

public class RetrieveDiffLambda implements RequestHandler<RetrieveDiffRequest, RetrieveDiffResponse> {
    private static final S3FileStructureConnector S3_FILE_STRUCTURE_CONNECTOR = new S3FileStructureConnector();
    private static final DiffExtractor DIFF_EXTRACTOR = new DiffExtractor();

    @Override
    public RetrieveDiffResponse handleRequest(RetrieveDiffRequest request, Context context) {
        request.preCheck();
        String id = request.getId();
        try {
            String diffString = S3_FILE_STRUCTURE_CONNECTOR.downloadDiff(id);
            Diff diff = DIFF_EXTRACTOR.extract(diffString);
            return new RetrieveDiffResponse(diff);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return ResponseUtil.clientError("Failed to retrieve diff");
        }
    }
}
