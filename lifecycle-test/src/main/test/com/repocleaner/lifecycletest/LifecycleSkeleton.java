package com.repocleaner.lifecycletest;

import com.repocleaner.coreclean.Cleaner;
import com.repocleaner.coredownload.Downloader;
import com.repocleaner.coresend.Sender;
import com.repocleaner.io.CleanIO;
import com.repocleaner.io.DownloadIO;
import com.repocleaner.io.RequestIO;
import com.repocleaner.io.SendIO;
import com.repocleaner.model.CleanResult;
import com.repocleaner.model.FileStructure;
import com.repocleaner.model.CleanRequest;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class LifecycleSkeleton implements RequestIO, DownloadIO, CleanIO, SendIO {
    private final FileStructure fileStructure;
    private final Consumer<CleanRequest> onRequested;
    private final Runnable onDownloaded;
    private final Consumer<CleanResult> onCleaned;
    private final Runnable onSent;

    @Override
    public void request(CleanRequest cleanRequest) throws RepoCleanerException {
        fileStructure.setLifecycleRequest(cleanRequest);
        onRequested.accept(cleanRequest);
        Downloader.download(this);
    }

    @Override
    public void downloaded() throws RepoCleanerException {
        onDownloaded.run();
        Cleaner.clean(this);
    }

    @Override
    public void cleaned(CleanResult cleanResult) throws RepoCleanerException {
        fileStructure.setCleanResult(cleanResult);
        onCleaned.accept(cleanResult);
        Sender.send(this);
    }

    @Override
    public void send() throws RepoCleanerException {
        onSent.run();
    }

    @Override
    public FileStructure getFileStructure() {
        return fileStructure;
    }
}
