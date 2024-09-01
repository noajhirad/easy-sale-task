package com.example.easy_saletask.viewmodel;

import android.content.Context;

import com.example.easy_saletask.model.Model;
import com.example.easy_saletask.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewModel {

    private ArrayList<UserViewModel> usersList;
    private final Model model;
    private static ViewModel viewModelInstance = null;
    private final Set<Integer> idSet = new HashSet<>();

    private ViewModel(Context context) {
        usersList = new ArrayList<>();
        model = new Model(context);
    }

    public static ViewModel getInstance(Context context) {
        if (viewModelInstance == null) {
            viewModelInstance = new ViewModel(context);
        }
        return viewModelInstance;
    }

    public static ViewModel getInstance() {
        return viewModelInstance;
    }

    public void initUsersList() {
        model.fetchAllUsers();
    }

    public ArrayList<UserViewModel> getUsersList() {
        List<User> list = model.getUsersList();
        this.usersList = new ArrayList<>();

        for (User user : list) {
            usersList.add(new UserViewModel(
                    user.getFirst_name(), user.getLast_name(), user.getEmail(),
                    user.getAvatar(), user.getId()));
            idSet.add(user.getId());
        }

        return usersList;
    }

    public void addNewUser(String firstName, String lastName, String email, String avatar, int id) {
        usersList.add(new UserViewModel(firstName, lastName, email, avatar, id));
        model.addNewUser(firstName, lastName, email, avatar, id);
        idSet.add(id);
    }

    public void removeUser(int pos) {
        UserViewModel user = usersList.get(pos);
        model.removeUser(user.getId());
        idSet.remove(user.getId());
    }

    public void updateUser(int position, String firstName, String lastName, String email, String avatar, int id) {
        model.updateUser(firstName, lastName, email, avatar, id);
    }

    public boolean checkIfIdExist(int id) {
        return idSet.contains(id);
    }
}
