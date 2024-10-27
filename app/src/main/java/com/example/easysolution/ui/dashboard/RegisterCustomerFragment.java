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

import com.example.easysolution.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterCustomerFragment extends Fragment {

    private FirebaseAuth auth; // FirebaseAuth instance
    private FirebaseFirestore db; // Firestore instance
    private EditText editTextName, editTextEmail, editTextPassword, editTextPhone, editTextAddress;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_customer, container, false);

        // Initialize FirebaseAuth and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Find views
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        Button buttonRegister = view.findViewById(R.id.buttonRegister);
        TextView textViewLogin = view.findViewById(R.id.textViewLogin);

        // Register button click listener
        buttonRegister.setOnClickListener(v -> registerUser());

        // Navigate to login on text click
        textViewLogin.setOnClickListener(v -> {
            // TODO: Navigate to Login Fragment
        });

        return view;
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register user with Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            // Store additional user info in Firestore
                            saveUserData(user.getUid(), name, phone, address);
                        }
                    } else {
                        // Registration failed
                        Toast.makeText(getContext(), "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserData(String userId, String name, String phone, String address) {
        // Create a user object
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("phone", phone);
        user.put("address", address);

        // Save user data in Firestore
        db.collection("customers").document(userId).set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "User data saved successfully", Toast.LENGTH_SHORT).show();
                    // TODO: Navigate back to the main dashboard
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to save user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
