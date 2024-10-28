package com.example.easysolution.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.easysolution.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private FirebaseAuth auth; // FirebaseAuth instance
    private EditText editTextEmail, editTextPassword;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Find views
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        TextView textViewRegister = view.findViewById(R.id.textViewRegister);

        // Login button click listener
        buttonLogin.setOnClickListener(v -> loginUser());

        // Navigate to register on text click
        textViewRegister.setOnClickListener(v -> {
            // TODO: Navigate to Register Fragment


            // Navigate to Register Fragment
            Navigation.findNavController(v).navigate(R.id.registerCustomerFragment);
        });

        return view;
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sign in with Firebase Authentication
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        // TODO: Navigate to Dashboard Fragment
                        Navigation.findNavController(getView()).navigate(R.id.navigation_dashboard);
                    } else {
                        // Login failed
                        Toast.makeText(getContext(), "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
