package com.example.easy_saletask;

import android.view.View;

public interface RecyclerViewInterface {
    void onDeleteClick(int position);

    void onEditClick(View view, int position, String firstName, String lastName, String email, String avatar, int id);
}
