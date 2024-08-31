package com.example.easy_saletask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easy_saletask.viewmodel.ViewModel;

public class AddUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText firstNameInput = findViewById(R.id.firstNameInput);
        EditText lastNameInput = findViewById(R.id.lastNameInput);
        EditText emailInput = findViewById(R.id.emailInput);
        EditText idInput = findViewById(R.id.idInput);
        EditText avatarInput = findViewById(R.id.avatarInput);
        Button saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(view -> saveNewUser(
                firstNameInput.getText().toString(),
                lastNameInput.getText().toString(),
                emailInput.getText().toString(),
                avatarInput.getText().toString(),
                Integer.parseInt(idInput.getText().toString())));
    }

    private void saveNewUser(String firstName, String lastName, String email, String avatar, int id) {
        ViewModel viewModel = ViewModel.getInstance();
        viewModel.addNewUser(firstName, lastName, email, avatar, id);

        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}