package com.example.easysolution.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
        TextView userInfoTextView = view.findViewById(R.id.user_info_text_view);
        Button logoutButton = view.findViewById(R.id.logout_button);

        Button loginButton = view.findViewById(R.id.login_button);
        Button registerButton = view.findViewById(R.id.register_customer_button);
        registerButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.registerCustomerFragment);
        });



        auth = FirebaseAuth.getInstance();


        // Check user authentication state
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            String userInfo = "Welcome, " + currentUser.getDisplayName(); // You can customize this to show more info
            userInfoTextView.setText(userInfo);
        } else {
            // No user is signed in
            userInfoTextView.setText("Welcome, Guest");
        }

        // Log Out button action
        logoutButton.setOnClickListener(v -> {
            auth.signOut();
            userInfoTextView.setText("Welcome, Guest"); // Update the UI after logging out
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}