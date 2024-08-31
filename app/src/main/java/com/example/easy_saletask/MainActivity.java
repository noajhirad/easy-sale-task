package com.example.easy_saletask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easy_saletask.viewmodel.UserViewModel;
import com.example.easy_saletask.viewmodel.ViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    ViewModel viewModel;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ActivityResultLauncher<Intent> addUserLauncher;
    ActivityResultLauncher<Intent> editUserLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = ViewModel.getInstance(getApplicationContext());
        viewModel.initUsersList();

        initRecyclerView();
        loadRecyclerView();

        addUserLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        loadRecyclerView();
                    }
                });

        editUserLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        loadRecyclerView();
                    }
                });

        Button addNewUserBtn = findViewById(R.id.addNewUserBtn);
        addNewUserBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddUserActivity.class);
            addUserLauncher.launch(intent);
        });
    }

    private void initRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<UserViewModel> list = viewModel.getUsersList();
        this.adapter = new RecyclerViewAdapter(
                getApplicationContext(), list, this, this);
        recyclerView.setAdapter(adapter);
    }

    private void loadRecyclerView() {
        ArrayList<UserViewModel> list = viewModel.getUsersList();
        adapter.setUsersList(list);
    }

    @Override
    public void onDeleteClick(int position) {
        viewModel.removeUser(position);
        loadRecyclerView();
    }

    @Override
    public void onEditClick(View view, int position, String firstName, String lastName,
                            String email, String avatar, int id) {
        Intent intent = new Intent(view.getContext(), EditUserActivity.class);
        intent.putExtra("FIRST-NAME", firstName);
        intent.putExtra("LAST-NAME", lastName);
        intent.putExtra("EMAIL", email);
        intent.putExtra("AVATAR", avatar);
        intent.putExtra("ID", id);
        intent.putExtra("POS", position);
        editUserLauncher.launch(intent);
    }
}