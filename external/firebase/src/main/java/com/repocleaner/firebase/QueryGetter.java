package com.repocleaner.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
public class QueryGetter<T> {
    private final Query query;
    private final Class<T> valueClass;

    public List<T> get() {
        if (query == null) {
            return null;
        }
        AtomicReference<List<T>> valueRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<T> result = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    T value = child.getValue(valueClass);
                    result.add(value);
                }
                valueRef.set(result);
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
