package com.repocleaner.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
public class FirebaseGetCommand<T> {
    private final DatabaseReference databaseReference;
    private final Class<T> valueClass;

    public T getValue() throws RepoCleanerException {
        AtomicReference<T> valueRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                T value = dataSnapshot.getValue(valueClass);
                valueRef.set(value);
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
            throw new RepoCleanerException("Failed when getting firebase value", e);
        }
        return valueRef.get();
    }
}
