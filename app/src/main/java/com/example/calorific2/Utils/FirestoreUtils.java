package com.example.calorific2.Utils;

import android.annotation.SuppressLint;

import com.example.calorific2.Manegment.User;
import com.example.calorific2.Manegment.MyApplication;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirestoreUtils {
    public static Task<Void> saveUserToFirestore(User user, MyApplication app) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    app.setUser(user);
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });
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
