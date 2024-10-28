package com.example.easysolution.ui.home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easysolution.R;
import java.util.List;

public class PopularServiceProviderAdapter extends RecyclerView.Adapter<PopularServiceProviderAdapter.ViewHolder> {
    private Context context;
    private List<ServiceProvider> providerList;

    public PopularServiceProviderAdapter(Context context, List<ServiceProvider> providerList) {
        this.context = context;
        this.providerList = providerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_provider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceProvider provider = providerList.get(position);
        holder.nameTextView.setText(provider.getName());
        holder.serviceTypeTextView.setText(provider.getServiceType());
        // You can set other fields here if necessary (like image, etc.)
    }

    @Override
    public int getItemCount() {
        return providerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView serviceTypeTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.providerNameTextView);
            serviceTypeTextView = itemView.findViewById(R.id.providerServiceTypeTextView);
            // Initialize other views here
        }
    }
}