package users.db;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
public class DbSelector<T> {
    private final DatabaseReference dbRef;
    private final Class<T> valueClass;
    private final String rawKey;
    private final boolean encoded;

    public T get() {
        String encodedKey = encoded
                ? KeyConverter.toKey(rawKey)
                : rawKey;
        AtomicReference<T> valueRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        dbRef.child(encodedKey).addListenerForSingleValueEvent(new ValueEventListener() {
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
