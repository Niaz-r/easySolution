package com.example.easysolution.ui.dashboard;



public class ServiceProvider {
    private String name;
    private String phone;
    private String email;
    private String location;
    private String serviceType;
    private String photoUrl;
    private String nid;

    public ServiceProvider() {
        // Required empty constructor for Firestore
    }

    public ServiceProvider(String name, String phone, String email, String location, String serviceType, String photoUrl, String nid) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.serviceType = serviceType;
        this.photoUrl = photoUrl;
        this.nid = nid;
    }

    // Getters and Setters (omitted for brevity)
}
