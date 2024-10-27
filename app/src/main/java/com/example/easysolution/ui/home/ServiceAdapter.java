package com.example.easysolution.ui.home;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysolution.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<ServiceItem> serviceList;
    private ServiceClickListener clickListener;  // This holds the click listener reference

    // Define the interface to handle service item clicks
    public interface ServiceClickListener {
        void onServiceClick(ServiceItem service);
    }

    // Constructor takes the service list and the click listener
    public ServiceAdapter(List<ServiceItem> serviceList, ServiceClickListener clickListener) {
        this.serviceList = serviceList;
        this.clickListener = clickListener;  // Assign the click listener
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the service item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        // Get the service item for the current position
        ServiceItem service = serviceList.get(position);

        // Set the service icon and name
        holder.iconImageView.setImageResource(service.getServiceIcon());
        holder.nameTextView.setText(service.getServiceName());

        // Set the click listener to the item
        holder.itemView.setOnClickListener(v -> clickListener.onServiceClick(service));
    }

    @Override
    public int getItemCount() {
        return serviceList.size();  // Return the number of services in the list
    }

    // ViewHolder class to represent the service item view
    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView nameTextView;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find the views from the service item layout
            iconImageView = itemView.findViewById(R.id.service_icon);
            nameTextView = itemView.findViewById(R.id.service_label);
        }
    }
}
