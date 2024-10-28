package com.example.easysolution.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easysolution.R;

import java.util.List;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ProviderViewHolder> {

    private List<ServiceProvider> providerList;

    public ServiceProviderAdapter(List<ServiceProvider> providerList) {
        this.providerList = providerList;
    }

    @NonNull
    @Override
    public ProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_provider_item, parent, false);
        return new ProviderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderViewHolder holder, int position) {

        ServiceProvider provider = providerList.get(position);
        holder.nameTextView.setText(provider.getName());
        holder.ratingTextView.setText("Rating: " + provider.getRating());
        holder.locationTextView.setText("Location: " + provider.getLocation());  // Display location
        holder.contactTextView.setText("Contact: " + provider.getContact());

        // Set click listener for the provider item

        // Load the provider's photo using Glide
        Glide.with(holder.itemView.getContext())
                .load(provider.getPhotoUrl())  // Load the photoUrl
                .placeholder(R.drawable.placeholder_image)  // Placeholder image
                .into(holder.photoImageView);  // Set the image into the ImageView

        holder.itemView.setOnClickListener(v -> {
            // Navigate to ProviderDetailFragment with provider details
            Bundle bundle = new Bundle();
            bundle.putString("providerId", provider.getId());
            bundle.putString("name", provider.getName());
          //  bundle.putString("email", provider.getEmail());
            bundle.putString("phone", provider.getContact());
            bundle.putString("location", provider.getLocation());
            bundle.putString("serviceType", provider.getServiceType());
            bundle.putString("photoUrl", provider.getPhotoUrl());

            Navigation.findNavController(v).navigate(R.id.providerDetailFragment, bundle);
        });

    }

    @Override
    public int getItemCount() {
        return providerList.size();
    }

    static class ProviderViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, ratingTextView, locationTextView, contactTextView;
        ImageView photoImageView;  // ImageView for the photo

        public ProviderViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.provider_name);
            ratingTextView = itemView.findViewById(R.id.provider_rating);
            locationTextView = itemView.findViewById(R.id.provider_location);
            contactTextView = itemView.findViewById(R.id.provider_contact);
            photoImageView = itemView.findViewById(R.id.provider_photo);  // ImageView for the photo
        }
    }
}

