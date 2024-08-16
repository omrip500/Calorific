package com.example.calorific2.Utils;

import android.annotation.SuppressLint;

import com.example.calorific2.Management.User;
import com.example.calorific2.Management.MyApplication;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class FirestoreUtils {
    public static Task<Void> saveUserToFirestore(User user, MyApplication app) {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(date);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        user.setLastDateUsingTheApp(currentDate);

        return db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> app.setUser(user))
                .addOnFailureListener(Throwable::printStackTrace);
    }


    public static void loadUserData(DocumentSnapshot document, MyApplication app) {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(date);

        if (document != null && document.exists()) {
            User user = document.toObject(User.class);
            if (user != null) {
                app.setUser(user);
                if(!user.getLastDateUsingTheApp().equals(currentDate)) {
                    user.resetFieldsForANewDay();
                    saveUserToFirestore(user, app);
                }
            } else {
                app.setUser(new User());
            }
        } else {
            app.setUser(new User());
        }
    }
}
