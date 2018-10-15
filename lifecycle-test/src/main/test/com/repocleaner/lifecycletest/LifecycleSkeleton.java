package com.repocleaner.lifecycletest;

import com.repocleaner.coreclean.Cleaner;
import com.repocleaner.coreprepare.Preparer;
import com.repocleaner.coresend.Sender;
import com.repocleaner.io.external.CleanIO;
import com.repocleaner.io.external.PrepareIO;
import com.repocleaner.io.external.ScheduleIO;
import com.repocleaner.io.external.SendIO;
import com.repocleaner.model.CleanResult;
import com.repocleaner.model.FileStructure;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class LifecycleSkeleton implements ScheduleIO, PrepareIO, CleanIO, SendIO {
    private final FileStructure fileStructure;
    private final Consumer<LifecycleRequest> onScheduled;
    private final Runnable onPrepared;
    private final Consumer<CleanResult> onCleaned;
    private final Runnable onSent;

    @Override
    public void schedule(LifecycleRequest lifecycleRequest) throws RepoCleanerException {
        fileStructure.setLifecycleRequest(lifecycleRequest);
        onScheduled.accept(lifecycleRequest);
        Preparer.prepare(this);
    }

    @Override
    public void prepared() throws RepoCleanerException {
        onPrepared.run();
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
