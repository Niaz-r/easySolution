package com.example.easysolution.ui.dashboard;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easysolution.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterServiceProviderFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextName, editTextPhone, editTextEmail, editTextNID;
    private TextView textViewLocation;
    private Button buttonFetchLocation, buttonSelectPhoto, buttonRegister;
    private Spinner spinnerServiceType;
    private Uri imageUri; // For the selected image
    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    private String[] serviceTypes = {"Electrician", "Plumber", "AC Mechanics", "Pest Control", "Cleaning", "Gardening", "Home Painting", "Pet Care", "Shifting"};
    private FusedLocationProviderClient fusedLocationClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_service_provider, container, false);

        // Initialize Firebase
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("service_provider_photos");

        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // Initialize UI elements
        editTextName = view.findViewById(R.id.editTextName);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextNID = view.findViewById(R.id.editTextNID);
        textViewLocation = view.findViewById(R.id.textViewLocation);
        buttonFetchLocation = view.findViewById(R.id.buttonFetchLocation);
        buttonSelectPhoto = view.findViewById(R.id.buttonSelectPhoto);
        buttonRegister = view.findViewById(R.id.buttonRegister);
        spinnerServiceType = view.findViewById(R.id.spinnerServiceType);

        // Set up the spinner for service types
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, serviceTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceType.setAdapter(adapter);

        // Fetch location on button click
        buttonFetchLocation.setOnClickListener(v -> fetchLocation());

        // Select photo from gallery
        buttonSelectPhoto.setOnClickListener(v -> openGallery());

        // Register service provider
        buttonRegister.setOnClickListener(v -> registerServiceProvider());

        return view;
    }

    private void fetchLocation() {
        // Check location permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
        }
        // Get the last known location
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    String locationText = "Lat: " + location.getLatitude() + ", Lon: " + location.getLongitude();
                    textViewLocation.setText(locationText); // Update TextView with coordinates
                } else {
                    Toast.makeText(getContext(), "Failed to get location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // You can use this imageUri to display the image in an ImageView if you want
        }
    }

    private void registerServiceProvider() {
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String nid = editTextNID.getText().toString().trim();
        String serviceType = spinnerServiceType.getSelectedItem().toString();
        String location = textViewLocation.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || nid.isEmpty() || location.isEmpty() || imageUri == null) {
            Toast.makeText(getContext(), "Please fill in all fields and select a photo", Toast.LENGTH_SHORT).show();
            return;
        }

        //ex



        //ey

        // Upload image to Firebase Storage and then save the provider details to Firestore
        uploadImageAndSaveProvider(name, phone, email, nid, serviceType, location);



    }

    private void uploadImageAndSaveProvider(String name, String phone, String email, String nid, String serviceType, String location) {
        // Logic to upload image and save provider information in Firestore goes here
        // Example:
        StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            // Get download URL and save to Firestore
            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                // Create a new provider object


                Map<String, Object> providerData = new HashMap<>();
                providerData.put("name", name);
                providerData.put("phone", phone);
                providerData.put("email", email);
                providerData.put("location", location);
                providerData.put("photoUrl", uri.toString()); // URL from Firebase Storage
                providerData.put("serviceType", serviceType);
                providerData.put("nidNumber", nid);

                // Save provider data to Firestore
                firestore.collection("serviceProviders")
                        .add(providerData)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(getContext(), "Service Provider Registered Successfully", Toast.LENGTH_SHORT).show();
                            clearFields(); // Clear input fields
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Failed to register provider: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });










            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void clearFields() {
        editTextName.setText("");
        editTextPhone.setText("");
        editTextEmail.setText("");
        editTextNID.setText("");
        textViewLocation.setText("Fetching location...");
        imageUri = null; // Reset image URI
    }
}

