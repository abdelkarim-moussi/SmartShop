package com.app.smartshop.domain.model;

import com.app.smartshop.domain.enums.LoyaltyLevel;

public class Client {
    private String id;
    private String name;
    private String email;
    private LoyaltyLevel loyaltyLevel;

    public Client(){}

    public Client(String id,String name, String email, LoyaltyLevel loyaltyLevel){
        this.id = id;
        this.name = name;
        this.email = email;
        this.loyaltyLevel = loyaltyLevel;
    }

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public LoyaltyLevel getLoyaltyLevel() {
        return loyaltyLevel;
    }

    public void setLoyaltyLevel(LoyaltyLevel loyaltyLevel) {
        this.loyaltyLevel = loyaltyLevel;
    }
}
