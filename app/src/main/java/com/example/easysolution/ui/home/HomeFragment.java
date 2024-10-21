package com.example.easysolution.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.easysolution.R;
import com.example.easysolution.databinding.ActivityMainBinding;
import com.example.easysolution.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ViewPager2 bannerViewPager;
    private int currentPage = 0;
    private Handler handler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Set up the ViewPager2 for banner slideshow using ViewBinding
        ViewPager2 bannerViewPager = binding.bannerViewPager;


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

        // Start the automatic slideshow
     //   startAutoSlideshow(bannerImages.size());

        // Initialize RecyclerView for services grid using ViewBinding
        RecyclerView servicesGrid = binding.servicesGrid;
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
        ServiceAdapter serviceAdapter = new ServiceAdapter(services);
        servicesGrid.setAdapter(serviceAdapter);
        return root;
    }

    private void startAutoSlideshow(int numPages) {
        // Create a runnable to change the page
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == numPages) {
                    currentPage = 0; // Reset to the first page if at the end
                }
                bannerViewPager.setCurrentItem(currentPage++, true); // Change the page
                handler.postDelayed(this, 3000); // Change page every 3 seconds
            }
        };
        handler.postDelayed(runnable, 3000); // Start the slideshow
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}