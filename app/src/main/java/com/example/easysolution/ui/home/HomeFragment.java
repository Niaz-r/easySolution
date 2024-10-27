package com.example.easysolution.ui.home;

import android.location.Address;
import android.location.Geocoder;
import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.easysolution.R;
import com.example.easysolution.databinding.ActivityMainBinding;
import com.example.easysolution.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ViewPager2 bannerViewPager;
    private int currentPage = 0;
    private Handler handler = new Handler();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView locationTextView;  // A TextView to display the location



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Reference the TextView where you want to display location
        locationTextView = root.findViewById(R.id.location_text_view);

        // Check if location permissions are granted
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Request permissions
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // If permissions are already granted, get location
            getCurrentLocation();
        }


        // Set up the ViewPager2 for banner slideshow using ViewBinding
        ViewPager2 bannerViewPager = binding.bannerViewPager;
        ViewPager2 bannerViewPager2 = binding.bannerViewPager2;

      //  ViewPager2 bannerViewPager = bannerViewPager.findViewById(R.id.banner_view_pager);

        // List of banner images (add your image resources here)
        List<Integer> bannerImages = Arrays.asList(
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3
        );


        // Set up the adapter with the banner images
        BannerAdapter bannerAdapter = new BannerAdapter(bannerImages);
        bannerViewPager.setAdapter(bannerAdapter);
        bannerViewPager.setAdapter(bannerAdapter);
        bannerViewPager2.setAdapter(bannerAdapter);

        // Start the automatic slideshow
     //   startAutoSlideshow(bannerImages.size());

        // Initialize RecyclerView for services grid using ViewBinding
        RecyclerView servicesGrid = root.findViewById(R.id.services_grid);
        //RecyclerView servicesGrid = binding.servicesGrid;
        servicesGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));  // 3 columns for the grid



        // Sample services list
        List<ServiceItem> services = new ArrayList<>();
        services.add(new ServiceItem(R.drawable.ic_electrician1, "Electrician"));
        services.add(new ServiceItem(R.drawable.ic_plumber, "Plumber"));
        services.add(new ServiceItem(R.drawable.ic_ac, "AC Mechanics"));
        services.add(new ServiceItem(R.drawable.ic_pest, "Pest Control"));
        services.add(new ServiceItem(R.drawable.ic_clean, "Cleaning"));
        services.add(new ServiceItem(R.drawable.ic_garden, "Gardening"));
        services.add(new ServiceItem(R.drawable.ic_home, "Home Painting"));
        services.add(new ServiceItem(R.drawable.ic_pet, "Pet Care"));
        services.add(new ServiceItem(R.drawable.ic_shift, "Shifting"));



        // Set up the adapter for RecyclerView
        ServiceAdapter serviceAdapter = new ServiceAdapter(services, service -> {
            // Pass the selected service name and navigate to the ServiceProvidersFragment
            Bundle bundle = new Bundle();
            bundle.putString("serviceType", service.getServiceName());

            // Navigate to ServiceProvidersFragment
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_serviceProvidersFragment, bundle);
        });
        servicesGrid.setAdapter(serviceAdapter);

        return root;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();  // Permissions granted, fetch the location
            } else {
                locationTextView.setText("Location permission denied.");
            }
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                // Convert latitude and longitude to an address using Geocoder
                                Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    if (addresses != null && !addresses.isEmpty()) {
                                        Address address = addresses.get(0);
                                        String addressText = address.getAddressLine(0);
                                        locationTextView.setText("Location: " + addressText);
                                    } else {
                                        locationTextView.setText("Address not available.");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    locationTextView.setText("Failed to get address.");
                                }
                            } else {
                                locationTextView.setText("Location not available.");
                            }
                        }
                    });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}