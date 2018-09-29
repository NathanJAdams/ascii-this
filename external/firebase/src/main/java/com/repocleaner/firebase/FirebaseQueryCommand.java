package com.repocleaner.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
public class FirebaseQueryCommand<T> {
    private final Query query;
    private final Class<T> valueClass;

    public Map<String, T> get() throws RepoCleanerException {
        AtomicReference<Map<String, T>> valueRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // keep insertion order
                Map<String, T> result = new LinkedHashMap<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    try {
                        T value = child.getValue(valueClass);
                        result.put(key, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                valueRef.set(result);
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RepoCleanerException("Failed when querying firebase", e);
        }
        return valueRef.get();
    }
}
