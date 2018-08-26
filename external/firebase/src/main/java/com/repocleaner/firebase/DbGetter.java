package com.repocleaner.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
public class DbGetter<T> {
    private final DatabaseReference dbRef;
    private final Class<T> valueClass;

    public T get() {
        if (dbRef == null) {
            return null;
        }
        AtomicReference<T> valueRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                T value = dataSnapshot.getValue(valueClass);
                valueRef.set(value);
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // no implementation
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return valueRef.get();
    }
}
