package com.example.calorific2.Utils;

import com.example.calorific2.Manegment.User;
import com.example.calorific2.Manegment.MyApplication;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        if (document != null && document.exists()) {
            User user = document.toObject(User.class);
            if (user != null) {
                app.setUser(user);
            } else {
                app.setUser(new User());
            }
        } else {
            app.setUser(new User());
        }
    }
}
