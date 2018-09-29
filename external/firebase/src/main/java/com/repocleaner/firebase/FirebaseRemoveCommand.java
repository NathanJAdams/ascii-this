package com.repocleaner.firebase;

import com.google.firebase.database.DatabaseReference;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@AllArgsConstructor
public class FirebaseRemoveCommand<T> {
    private final DatabaseReference databaseReference;

    public boolean remove() throws RepoCleanerException {
        AtomicBoolean success = new AtomicBoolean();
        CountDownLatch latch = new CountDownLatch(1);
        databaseReference.removeValue((error, ref) -> {
            success.set(error != null);
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RepoCleanerException("Failed when removing firebase value", e);
        }
        return success.get();
    }
}
