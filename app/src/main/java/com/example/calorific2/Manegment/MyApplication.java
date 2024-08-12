package com.example.calorific2.Manegment;

import android.app.Application;

public class MyApplication extends Application {
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        user = new User("Omri", "Peer", 23, 70, 2000, 0, 0, 0, 0, 0);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
