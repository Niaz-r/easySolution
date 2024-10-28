package com.example.easysolution.ui.home;
public class ServiceProvider {
    private String id;
    private String name;
    private String rating;
    private String location;  // New field
    private String contact;
    private String serviceType;
    private String photoUrl;  // New field

    public ServiceProvider() {
    }
    // Constructor
    public ServiceProvider(String id,String name, String rating, String location, String contact, String serviceType, String photoUrl) {

        this.id = id;
        this.name = name;
        this.rating = rating;
        this.location = location;
        this.contact = contact;
        this.serviceType = serviceType;
        this.photoUrl = photoUrl;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}


