package com.example.easysolution.ui.home;

public class ServiceItem {
    private int icon;
    private String label;

    public ServiceItem(int icon, String label) {
        this.icon = icon;
        this.label = label;
    }

    public int getIcon() {
        return icon;
    }

    public String getLabel() {
        return label;
    }
    // Getter for service icon
    public int getServiceIcon() {
        return icon;
    }

    // Getter for service name
    public String getServiceName() {
        return label;
    }
}

