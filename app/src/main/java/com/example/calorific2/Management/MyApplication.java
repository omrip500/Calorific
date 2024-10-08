package com.example.calorific2.Management;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyApplication extends Application {
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();
            db.collection("users").document(userId).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                user = document.toObject(User.class);
                            } else {
                                user = new User();
                            }
                        } else {
                            Exception exception = task.getException();
                            if (exception != null) {
                                Log.e("TAG", "Task failed with exception: ", exception);
                            } else {
                                Log.e("TAG", "Task failed with an unknown error.");
                            }
                        }
                    });
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
