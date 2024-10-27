package com.example.easysolution.ui.home;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysolution.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.List;

public class ServiceProvidersFragment extends Fragment {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ServiceProviderAdapter adapter;
    private List<ServiceProvider> serviceProviderList;
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_service_providers, container, false);
        db = FirebaseFirestore.getInstance();

        recyclerView = root.findViewById(R.id.service_providers_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        serviceProviderList = new ArrayList<>();
        adapter = new ServiceProviderAdapter(serviceProviderList);
        recyclerView.setAdapter(adapter);

        // Get the service type passed from the HomeFragment
        String serviceType = getArguments().getString("serviceType");

        // Fetch service providers from Firebase based on service type
        fetchServiceProviders(serviceType);

        return root;
    }

    private void fetchServiceProviders(String serviceType) {
        db.collection("serviceProviders")
                .whereEqualTo("serviceType",serviceType)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        serviceProviderList.clear(); // Clear previous data
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Create a ServiceProvider object with the additional fields
                            ServiceProvider provider = new ServiceProvider(
                                    document.getString("name"),
                                    document.getString("rating"),
                                    document.getString("location"),  // Fetch location
                                    document.getString("contact"),
                                    document.getString("serviceType"),
                                    document.getString("photoUrl")   // Fetch photoUrl
                            );
                            serviceProviderList.add(provider);
                        }
                        // Set up the adapter with the fetched data
                        adapter = new ServiceProviderAdapter(serviceProviderList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.w("Firebase", "Error getting documents.", task.getException());
                        Toast.makeText(getContext(), "Failed to load providers", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Add this to listen for back button press and navigate back to HomeFragment
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = NavHostFragment.findNavController(ServiceProvidersFragment.this);
                navController.navigate(R.id.navigation_home); // Navigates back to HomeFragment

            }
        });
    }


}
