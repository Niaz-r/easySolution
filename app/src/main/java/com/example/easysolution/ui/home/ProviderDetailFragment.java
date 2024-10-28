package com.example.easysolution.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.easysolution.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ProviderDetailFragment extends Fragment {

    private FirebaseFirestore db;

    private ImageView providerImage;
    private TextView providerName;
    private TextView providerEmail;
    private TextView providerPhone;
    private TextView providerLocation;
    private TextView providerServiceType;
    private TextView providerRating;
    private Button hireNowButton;
    private Button giveReviewButton;
    private Button reportButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_provider_detail, container, false);

        // Initialize views
        providerImage = root.findViewById(R.id.provider_photo);
        providerName = root.findViewById(R.id.provider_name);
        providerEmail = root.findViewById(R.id.provider_email);
        providerPhone = root.findViewById(R.id.provider_phone);
        providerLocation = root.findViewById(R.id.provider_location);
        providerServiceType = root.findViewById(R.id.provider_service_type);

        hireNowButton = root.findViewById(R.id.hire_now_button);
        giveReviewButton = root.findViewById(R.id.give_review_button);
        reportButton = root.findViewById(R.id.report_button);

        db = FirebaseFirestore.getInstance();

        // Get the provider ID passed from the previous fragment
        String providerId = getArguments().getString("providerId");
        if (providerId != null) {
            loadProviderDetails(providerId);
        }

        // Set button click listeners
        hireNowButton.setOnClickListener(v -> {
            // Add hire logic here
            String providerPhoneNumber = getArguments().getString("phone"); // Pass this argument when navigating
            if (providerPhoneNumber != null) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + providerPhoneNumber)); // Format the number as a tel URI
                startActivity(intent);
            }

        });

        giveReviewButton.setOnClickListener(v -> {
            // Add review logic here
        });

        reportButton.setOnClickListener(v -> {
            // Add report logic here
        });

        return root;
    }

    private void loadProviderDetails(String providerId) {
        db.collection("serviceProviders").document(providerId).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        // Populate the views with data
                        providerName.setText(document.getString("name"));
                        providerEmail.setText(document.getString("email"));
                        providerPhone.setText(document.getString("phone"));
                        providerLocation.setText(document.getString("location"));
                        providerServiceType.setText(document.getString("serviceType"));
                     //   providerRating.setText(document.getString("rating"));
                        Glide.with(this)
                                .load(document.getString("photoUrl")) // Load the image using Glide
                                .into(providerImage);
                    }
                });
    }
}
