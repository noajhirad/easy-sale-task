package com.example.easy_saletask.viewmodel;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class UserViewModel {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String avatar;
    private final int id;

    public UserViewModel(String firstName, String lastName, String email, String avatar, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }
}
