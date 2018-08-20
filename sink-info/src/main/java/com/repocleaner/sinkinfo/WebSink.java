package com.repocleaner.sinkinfo;

import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;

@AllArgsConstructor
@Getter
public class WebSink implements Sink {
    private final String endpoint;
    private final WebResponseType webResponseType;

    @Override
    public void sendSource(File sourceFolder, File tempFile) throws RepoCleanerException {
        webResponseType.saveTo(sourceFolder, tempFile);
        HttpPost post = new HttpPost(endpoint);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        FileBody fileBody = new FileBody(tempFile, ContentType.DEFAULT_BINARY);
        builder.addPart("cleaned", fileBody);
        HttpEntity entity = builder.build();
        post.setEntity(entity);
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            client.execute(post);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to send cleaned code to end point", e);
        }
    }
}
