package com.example.easysolution.ui.dashboard;

import  android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.easysolution.R;
import com.example.easysolution.databinding.FragmentDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private FirebaseAuth auth; // Declare FirebaseAuth here

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        TextView userInfoTextView = view.findViewById(R.id.textViewUserName);
        Button logoutButton = view.findViewById(R.id.logout_button);

        Button loginButton = view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.navigation_login);
        });

        Button registerButton = view.findViewById(R.id.register_customer_button);
        registerButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.registerCustomerFragment);
        });


        Button registerServiceProviderButton = view.findViewById(R.id.register_service_provider_button);
        registerServiceProviderButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.navigation_register_service_provider);
        });

        // DashboardActivity.java or DashboardFragment.java







        auth = FirebaseAuth.getInstance();


        // Check user authentication state
        FirebaseUser currentUser = auth.getCurrentUser();


        //hi

        TextView textViewUserName = view.findViewById(R.id.textViewUserName);
        //start

        if (currentUser != null) {
            String userName = currentUser.getDisplayName(); // Get display name
            if (userName != null) {
                textViewUserName.setText("Welcome, " + userName); // Display user name
            } else {
                textViewUserName.setText("Welcome!"); // Fallback if no display name
            }
        } else {
            textViewUserName.setText("Welcome, Guest"); // If not logged in
        }



        // Log Out button action
        logoutButton.setOnClickListener(v -> {
            auth.signOut();
            userInfoTextView.setText("Guest Mood On"); // Update the UI after logging out
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}