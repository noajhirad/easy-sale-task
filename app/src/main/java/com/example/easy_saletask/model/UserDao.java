package com.example.easy_saletask.model;

import androidx.core.location.LocationRequestCompat;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    public List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user WHERE id = :id")
    void deleteUserByID(int id);

    @Query("UPDATE user SET first_name = :firstName, last_name = :lastName, email = :email, avatar = :avatar WHERE id = :id")
    void updateUserByID(int id, String firstName, String lastName, String email, String avatar);

    @Query("SELECT COUNT(*) FROM user")
    int getUsersCount();
}
