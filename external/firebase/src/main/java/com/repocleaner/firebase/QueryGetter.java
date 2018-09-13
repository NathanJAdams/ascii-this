package com.repocleaner.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import lombok.AllArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
public class QueryGetter<T> {
    private final Query query;
    private final Class<T> valueClass;

    public Map<String, T> get() {
        if (query == null) {
            return null;
        }
        AtomicReference<Map<String, T>> valueRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // keep insertion order
                Map<String, T> result = new LinkedHashMap<>();
                System.out.println("loop child snapshots");
                System.out.println("children: " + dataSnapshot.getChildrenCount());
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    System.out.println("Key: " + key);
                    try {
                        T value = child.getValue(valueClass);
                        System.out.println("Value: " + value);
                        result.put(key, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("looped child snapshots");
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
