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

public class EditUserActivity extends AppCompatActivity {

    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText avatarInput;
    Button saveBtn;
    int position;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        avatarInput = findViewById(R.id.avatarInput);
        saveBtn = findViewById(R.id.saveBtn);

        setInputsText();

        saveBtn.setOnClickListener(view -> {
            updateUser(
                    position,
                    firstNameInput.getText().toString(),
                    lastNameInput.getText().toString(),
                    emailInput.getText().toString(),
                    avatarInput.getText().toString(),
                    id);
        });
    }

    private void setInputsText() {
        firstNameInput.setText(getIntent().getStringExtra("FIRST-NAME"));
        lastNameInput.setText(getIntent().getStringExtra("LAST-NAME"));
        emailInput.setText(getIntent().getStringExtra("EMAIL"));
        avatarInput.setText(getIntent().getStringExtra("AVATAR"));
        position = getIntent().getIntExtra("POS",0);
        id = getIntent().getIntExtra("ID",0);
    }

    private void updateUser(int position, String firstName, String lastName,
                            String email, String avatar, int id) {
        ViewModel viewModel = ViewModel.getInstance();
        viewModel.updateUser(position,firstName,lastName,email,avatar, id);

        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}