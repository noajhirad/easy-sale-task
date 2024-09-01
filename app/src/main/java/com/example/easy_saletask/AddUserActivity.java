package com.example.easy_saletask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easy_saletask.viewmodel.ViewModel;

public class AddUserActivity extends AppCompatActivity {

    TextView tvError;
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText idInput;
    EditText avatarInput;
    Button saveBtn;

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

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        idInput = findViewById(R.id.idInput);
        avatarInput = findViewById(R.id.avatarInput);
        saveBtn = findViewById(R.id.saveBtn);
        tvError = findViewById(R.id.errorTextView);
        tvError.setText("");

        saveBtn.setOnClickListener(view -> {
            if (checkAllFieldsAreFilled()){
                saveNewUser();
            } else {
                tvError.setText("Please fill all of the fields.");
            }
        });
    }

    private void saveNewUser() {
        ViewModel viewModel = ViewModel.getInstance();
        int id = Integer.parseInt(idInput.getText().toString());
        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String avatar = avatarInput.getText().toString();

        if (!viewModel.checkIfIdExist(id)) {
            viewModel.addNewUser(firstName, lastName, email, avatar, id);

            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } else {
            tvError.setText("ID is already in use. Please select another user ID.");
        }
    }

    private boolean checkAllFieldsAreFilled() {
        return !firstNameInput.getText().toString().isEmpty() &&
                !lastNameInput.getText().toString().isEmpty() &&
                !emailInput.getText().toString().isEmpty() &&
                !avatarInput.getText().toString().isEmpty() &&
                !idInput.getText().toString().isEmpty();
    }
}