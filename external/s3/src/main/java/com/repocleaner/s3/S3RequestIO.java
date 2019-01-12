package com.repocleaner.s3;

import com.repocleaner.io.RequestIO;
import com.repocleaner.model.FileStructure;
import com.repocleaner.model.CleanRequest;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3RequestIO implements RequestIO {
    private final JsonUtil jsonUtil;

    @Override
    public void request(CleanRequest cleanRequest) throws RepoCleanerException {
        String id = cleanRequest.getId();
        FileStructure fileStructure = new FileStructure(S3Info.TMP_FOLDER, id, jsonUtil);
        fileStructure.setLifecycleRequest(cleanRequest);
        S3FileStructure.upload(fileStructure, Constants.BUCKET_REQUESTED);
    }
}
