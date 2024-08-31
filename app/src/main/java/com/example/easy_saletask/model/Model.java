package com.example.easy_saletask.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Model {

    interface UsersService {
        @GET("/api/users")
        Call<UsersData> getUsersData(@Query("page") int page);
    }

    private final Retrofit retrofit;
    private final UsersService service;
    private UsersData data;
    private final AppDatabase db;
    private final UserDao userDao;

    public Model(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(UsersService.class);

        db = Room.databaseBuilder(context, AppDatabase.class, "app-database").allowMainThreadQueries().build();
        userDao = db.userDao();
    }

    private void fetchUsers(int page) {
        if (userDao.getUsersCount() == 0) {
            service.getUsersData(page).enqueue(new Callback<UsersData>() {
                @Override
                public void onResponse(@NonNull Call<UsersData> call, @NonNull Response<UsersData> response) {
                    data = response.body();
                    if (data != null && data.getData() != null) {
                        userDao.insertAll(data.getData());

                        if (data.getPage() < data.getTotal_pages()) {
                            fetchUsers(page + 1);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UsersData> call, @NonNull Throwable throwable) {
                    Log.d("ERROR", Objects.requireNonNull(throwable.getMessage()));
                }
            });
        }
    }

    public void fetchAllUsers() {
        fetchUsers(1);
    }

    public List<User> getUsersList() {
        return userDao.getAll();
    }

    public void addNewUser(String firstName, String lastName, String email, String avatar, int id) {
        userDao.insert(new User(firstName, lastName, email, avatar, id));
    }

    public void removeUser(int id) {
        userDao.deleteUserByID(id);
    }

    public void updateUser(String firstName, String lastName, String email, String avatar, int id) {
        userDao.updateUserByID(id, firstName, lastName, email, avatar);
    }
}


