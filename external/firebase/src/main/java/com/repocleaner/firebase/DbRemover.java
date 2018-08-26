package com.repocleaner.firebase;

import com.google.firebase.database.DatabaseReference;
import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@AllArgsConstructor
public class DbRemover<T> {
    private final DatabaseReference dbRef;

    public boolean remove() {
        if (dbRef == null) {
            return false;
        }
        AtomicBoolean success = new AtomicBoolean();
        CountDownLatch latch = new CountDownLatch(1);
        dbRef.removeValue((error, ref) -> {
            success.set(error != null);
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return success.get();
    }
}
